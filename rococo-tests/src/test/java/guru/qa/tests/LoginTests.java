package guru.qa.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.pages.LoginPage;
import org.junit.jupiter.api.Test;

public class LoginTests extends BaseWebTest {

    private static final String DEFAULT_PASSWORD = "12345";
    private static final String BAD_CREDENTIALS_MESSAGE = "Неверные учетные данные пользователя";

    @DBUser
    @Test
    void loginBtnShouldNotBeVisibleAfterAuthorization(AuthUserEntity user) {
        Selenide.open(CFG.rococoFrontUrl());
        pages.mainPage()
                .getLoginButton()
                .click();
        pages.loginPage()
                .getUsernameField()
                .setValue(user.getUsername());
        pages.loginPage()
                .getPasswordField()
                .setValue(user.getPassword());
        pages.loginPage()
                .getLoginBtn()
                .click();
        pages.mainPage()
                .getLoginButton()
                .shouldNotBe(Condition.visible);
        pages.mainPage()
                .getProfileIcon()
                .shouldBe(Condition.visible);
    }

    @Test
    void loginWithBadCredentialsTest() {
        Selenide.open(CFG.rococoAuthUrl() + LoginPage.URL);

        pages.loginPage()
                .getUsernameField()
                .setValue(new Faker().name().username());
        pages.loginPage()
                .getPasswordField()
                .setValue(DEFAULT_PASSWORD);
        pages.loginPage()
                .getLoginBtn()
                .click();
        pages.loginPage()
                .getErrorNotification()
                .shouldHave(Condition.text(BAD_CREDENTIALS_MESSAGE));
    }
}
