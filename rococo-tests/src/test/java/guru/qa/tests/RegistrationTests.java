package guru.qa.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.pages.LoginPage;
import guru.qa.pages.RegistrationPage;
import org.junit.jupiter.api.Test;

public class RegistrationTests extends BaseWebTest {
    private static final String DEFAULT_PASSWORD = "12345";
    private final Faker faker = new Faker();

    @Test
    void mainPageShouldBeVisibleAfterRegistration() {
        String username = faker.name().username();
        Selenide.open(CFG.rococoAuthUrl() + LoginPage.URL);
        pages.loginPage()
                .getRegisterBtn()
                .click();
        pages.registrationPage()
                .getUsernameField()
                .setValue(username);
        pages.registrationPage()
                .getPasswordField()
                .setValue(DEFAULT_PASSWORD);
        pages.registrationPage()
                .getPasswordSubmitField()
                .setValue(DEFAULT_PASSWORD);
        pages.registrationPage()
                .getRegisterBtn()
                .click();
        pages.registrationPage()
                .getLoginBtn()
                .click();
        pages.loginPage()
                .getUsernameField()
                .setValue(username);
        pages.loginPage()
                .getPasswordField()
                .setValue(DEFAULT_PASSWORD);
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
    @DBUser
    void registerExistingUser(AuthUserEntity user) {
        Selenide.open(CFG.rococoAuthUrl() + RegistrationPage.URL);

        pages.registrationPage()
                .getUsernameField()
                .setValue(user.getUsername());
        pages.registrationPage()
                .getPasswordField()
                .setValue(user.getPassword());
        pages.registrationPage()
                .getPasswordSubmitField()
                .setValue(user.getPassword());
        pages.registrationPage()
                .getRegisterBtn()
                .click();
        pages.registrationPage()
                .getErrorNotification()
                .shouldHave(Condition.text("Username `" + user.getUsername() + "` already exists"));
    }
}
