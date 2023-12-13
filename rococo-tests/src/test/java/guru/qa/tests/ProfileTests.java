package guru.qa.tests;

import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.pages.MainPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomName;
import static guru.qa.utils.FakerUtils.generateRandomSurname;

@Epic("Профиль")
@DisplayName("Профиль")
public class ProfileTests extends BaseWebTest {

    public static final String imgPath = "images/scream.jpg";

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Обновление данных в профиле")
    void profileDataShouldBeUpdated() {
        String name = generateRandomName();
        String surname = generateRandomSurname();
        page(MainPage.class)
                .open()
                .clickProfileIcon()
                .enterValues(name, surname, imgPath)
                .clickUpdateBtn();
        page(MainPage.class)
                .checkNotificationMessage("Профиль обновлен");
    }


    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверка кнопки закрыть в попапе профиля")
    void profileDataShouldNotBeUpdated() {
        String name = generateRandomName();
        String surname = generateRandomSurname();
        page(MainPage.class)
                .open()
                .clickProfileIcon()
                .enterValues(name, surname, imgPath)
                .clickCloseBtn();
        page(MainPage.class)
                .notificationMessageShouldNotBeVisible();
    }
}
