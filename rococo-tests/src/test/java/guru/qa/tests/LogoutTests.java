package guru.qa.tests;

import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.pages.MainPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;

@Epic("Авторизация")
@DisplayName("Авторизация")
public class LogoutTests extends BaseWebTest {

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Выход из системы")
    void logoutBtnShouldBeVisibleAfterLogout(AuthUserEntity user) {
        page(MainPage.class).open()
                .clickProfileIcon()
                .clickLogoutBtn(MainPage.class)
                .loginButtonShouldBeVisible()
                .profileIconShouldNotBeVisible();
    }
}
