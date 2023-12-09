package guru.qa.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import guru.qa.config.Config;
import guru.qa.api.AuthServiceClient;
import guru.qa.api.context.CookieContext;
import guru.qa.api.context.LocalStorageContext;
import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import java.io.IOException;

public class ApiLoginExtension implements BeforeEachCallback, AfterTestExecutionCallback {

    private final AuthServiceClient authServiceClient = new AuthServiceClient();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        ApiLogin apiLoginAnnotation = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (apiLoginAnnotation != null) {
            AuthUserEntity authUserEntity = ((AuthUserEntity) extensionContext.getStore(DBUserExtension.NAMESPACE).get(extensionContext.getUniqueId()));
            String username = apiLoginAnnotation.username().isEmpty() ?
                    authUserEntity.getUsername() : apiLoginAnnotation.username();
            String password = apiLoginAnnotation.username().isEmpty() ?
                    authUserEntity.getPassword() : apiLoginAnnotation.password();

            doLogin(username, password);
        }
    }

    private void doLogin(String username, String password) {
        LocalStorageContext localStorageContext = LocalStorageContext.getInstance();
        localStorageContext.init();

        try {
            authServiceClient.doLogin(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Selenide.open(Config.getInstance().rococoFrontUrl());
        Selenide.localStorage().setItem("codeChallenge", localStorageContext.getCodeChallenge());
        Selenide.localStorage().setItem("id_token", localStorageContext.getToken());
        Selenide.localStorage().setItem("codeVerifier", localStorageContext.getCodeVerifier());
        Cookie jsessionIdCookie = new Cookie("JSESSIONID", CookieContext.getInstance().getJSessionIdCookieValue());
        WebDriverRunner.getWebDriver().manage().addCookie(jsessionIdCookie);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        LocalStorageContext.getInstance().clearContext();
        CookieContext.getInstance().clearContext();
    }
}