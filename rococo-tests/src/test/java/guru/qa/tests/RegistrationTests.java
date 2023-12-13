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

    @Test
    @DisplayName("Регистрация несуществующего в бд пользователя")
    void mainPageShouldBeVisibleAfterRegistration() {
        String username = generateRandomUsername();

        page(LoginPage.class)
                .open()
                .clickRegisterButton()
                .enterCredentials(username, DEFAULT_PASSWORD)
                .clickRegisterBtn()
                .clickLoginBtn()
                .enterCredentials(username, DEFAULT_PASSWORD)
                .clickLoginButton();

        page(MainPage.class)
                .loginButtonShouldNotBeVisible()
                .profileIconShouldBeVisible();
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
}
