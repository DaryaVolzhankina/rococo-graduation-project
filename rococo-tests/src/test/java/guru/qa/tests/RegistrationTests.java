package guru.qa.tests;

import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.pages.LoginPage;
import guru.qa.pages.MainPage;
import guru.qa.pages.RegistrationPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomUsername;

@Epic("Регистрация")
@DisplayName("Регистрация")
public class RegistrationTests extends BaseWebTest {
    private static final String DEFAULT_PASSWORD = "12345";
    private static final String LONG_PASSWORD = "1234512345123";

    @Test
    @DisplayName("Регистрация несуществующего в бд пользователя")
    void mainPageShouldBeVisibleAfterRegistration() {
        String username = generateRandomUsername();

        page(LoginPage.class)
                .open()
                .clickRegisterButton()
                .enterCredentials(username, DEFAULT_PASSWORD)
                .clickRegisterBtn()
                .clickEnterSystemBtn()
                .enterCredentials(username, DEFAULT_PASSWORD)
                .clickLoginButton();

        page(MainPage.class)
                .loginButtonShouldNotBeVisible()
                .profileIconShouldBeVisible();
    }

    @Test
    @DisplayName("Регистрация с пустым полем Повторите пароль")
    void registrationWithEmptyPasswordSubmitField() {
        page(RegistrationPage.class)
                .open()
                .enterCredentialsWithoutPasswordSubmitField(generateRandomUsername(), DEFAULT_PASSWORD)
                .clickRegisterBtn()
                .checkPageIsOpened();
    }

    @Test
    @DisplayName("Регистрация с несовпадающими полями Пароль и Повторите пароль")
    void registrationWithDifferentPasswordFieldPasswordSubmitField() {
        page(RegistrationPage.class)
                .open()
                .enterRandomCredentials()
                .clickRegisterBtn()
                .checkPageIsOpened()
                .checkErrorNotificationMessage("Passwords should be equal", RegistrationPage.class);
    }

    @Test
    @DisplayName("Регистрация с паролем больше 12 символов")
    void registrationWithPasswordLonger12Char() {
        page(RegistrationPage.class)
                .open()
                .enterCredentials(generateRandomUsername(), LONG_PASSWORD)
                .clickRegisterBtn()
                .checkPageIsOpened()
                .checkErrorNotificationMessage("Allowed password length should be from 3 to 12 characters", RegistrationPage.class);
    }

    @Test
    @DBUser
    @DisplayName("Регистрация существующего в бд пользователя")
    void registerExistingUser(AuthUserEntity user) {
        page(LoginPage.class)
                .open()
                .clickRegisterButton()
                .enterCredentials(user.getUsername(), DEFAULT_PASSWORD)
                .clickRegisterBtn()
                .checkErrorNotificationMessage("Username `" + user.getUsername() + "` already exists", RegistrationPage.class);
    }

    @Test
    @DisplayName("Проверить кнопку Войти")
    void checkLoginButton() {
        page(RegistrationPage.class)
                .open()
                .clickLoginBtn()
                .checkPageIsOpened();
    }
}
