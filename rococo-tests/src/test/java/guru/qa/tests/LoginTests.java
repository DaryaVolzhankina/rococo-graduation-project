package guru.qa.tests;

import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.pages.LoginPage;
import guru.qa.pages.MainPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomUsername;

@Epic("Авторизация")
@DisplayName("Авторизация")
public class LoginTests extends BaseWebTest {

    private static final String DEFAULT_PASSWORD = "12345";
    private static final String BAD_CREDENTIALS_MESSAGE = "Неверные учетные данные пользователя";

    @DBUser
    @Test
    @DisplayName("Авторизация с существующими в бд логином/паролем")
    void loginBtnShouldNotBeVisibleAfterAuthorization(AuthUserEntity user) {
        page(MainPage.class).open()
                .clickLoginButton()
                .enterCredentials(user.getUsername(), user.getPassword())
                .clickLoginButton();
        page(MainPage.class)
                .loginButtonShouldNotBeVisible()
                .profileIconShouldBeVisible();
    }

    @Test
    @DisplayName("Авторизация с несуществующими в бд логином/паролем")
    void loginWithBadCredentialsTest() {
        page(MainPage.class).open()
                .clickLoginButton()
                .enterCredentials(generateRandomUsername(), DEFAULT_PASSWORD)
                .clickLoginButton();
        page(LoginPage.class).checkErrorNotificationMessage(BAD_CREDENTIALS_MESSAGE, LoginPage.class);
    }

    @Test
    @DisplayName("Авторизация с незаполненными полями логин,пароль")
    void loginWithEmptyCredentialsTest() {
        page(LoginPage.class).open()
                .clickLoginButton();
        page(LoginPage.class)
                .checkPageIsOpened();
    }
}
