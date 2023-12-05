package guru.qa.niffler.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.DBUser;
import org.junit.jupiter.api.Test;

public class LogoutTests extends BaseWebTest {

    @DBUser
    @ApiLogin
    @Test
    void logoutBtnShouldBeVisibleAfterLogout(AuthUserEntity user) {
        Selenide.open(CFG.rococoFrontUrl());

        pages.mainPage()
                .getProfileIcon()
                .click();
        pages.profilePage()
                .getLogoutBtn()
                .click();
        pages.mainPage()
                .getLoginButton()
                .shouldBe(Condition.visible);
        pages.mainPage()
                .getProfileIcon()
                .shouldNotBe(Condition.visible);
    }
}
