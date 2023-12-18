package guru.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.api.context.CookieContext;
import guru.qa.api.context.LocalStorageContext;
import guru.qa.api.interceptor.AddCookieInterceptor;
import guru.qa.api.interceptor.ReceivedCodeInterceptor;
import guru.qa.api.interceptor.ReceivedCookieInterceptor;
import io.qameta.allure.Step;

import java.io.IOException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AuthServiceClient extends RestService {
    public AuthServiceClient() {
        super(config.rococoAuthUrl(), true,
                new ReceivedCookieInterceptor(),
                new AddCookieInterceptor(),
                new ReceivedCodeInterceptor()
        );
    }

    private final AuthService authService = retrofit.create(AuthService.class);

    @Step("Авторизоваться пользователем {0}")
    public void doLogin(String username, String password) throws IOException {
        LocalStorageContext localStorageContext = LocalStorageContext.getInstance();
        CookieContext cookieContext = CookieContext.getInstance();
        authService.authorize(
                "code",
                "client",
                "openid",
                config.rococoFrontUrl() + "/authorized",
                localStorageContext.getCodeChallenge(),
                "S256"
        ).execute();

        authService.login(
                username,
                password,
                cookieContext.getXsrfTokenCookieValue()
        ).execute();

        JsonNode response = authService.token(
                "Basic " + new String(Base64.getEncoder().encode("client:secret".getBytes(UTF_8))),
                "client",
                config.rococoFrontUrl() + "/authorized",
                "authorization_code",
                localStorageContext.getCode(),
                localStorageContext.getCodeVerifier()
        ).execute().body();

        localStorageContext.setToken(response.get("id_token").asText());
    }
}