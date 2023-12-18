package guru.qa.tests;

import com.github.javafaker.Faker;
import guru.qa.db.model.museum.MuseumEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.jupiter.annotation.GeneratedMuseum;
import guru.qa.jupiter.annotation.Museum;
import guru.qa.pages.MainPage;
import guru.qa.pages.MuseumPage;
import guru.qa.pages.MuseumsPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomSentence;

@Epic("Музеи")
@DisplayName("Страница Музеи")
public class MuseumTests extends BaseWebTest {

    public static final String name = new Faker().company().name();
    public static final String description = name + generateRandomSentence(6);
    public static final String imgPath = "images/museum.png";
    public static final String museumAddedNotificationMessage = "Добавлен музей: " + name;

    @Museum
    @Test
    @DisplayName("Переход с главной страницы на страницу Музеи")
    void checkMuseumsPageTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum) {
        page(MainPage.class)
                .open()
                .clickMuseumsBtn()
                .checkPageIsOpened()
                .checkMuseumsNamesList();
    }

    @Museum
    @Test
    @DisplayName("Поиск музея")
    void searchMuseumTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum) {
        page(MuseumsPage.class)
                .open()
                .searchMuseum(museum.getTitle())
                .checkMuseumsNamesList(museum.getTitle());
    }

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверка добаления музея")
    void addMuseumTest() {
        page(MuseumsPage.class)
                .open()
                .clickAddMuseumBtn()
                .enterValues(name, "Russia", "Moscow", imgPath, description)
                .clickAddBtn()
                .checkNotificationMessage(museumAddedNotificationMessage, MuseumsPage.class)
                .searchMuseum(name)
                .getMuseum()
                .checkMuseum(name, description, "Moscow", "Russia");
    }

    @Museum
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверка редактирования музея")
    void editMuseumTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum) {
        String name = new Faker().company().name();
        String description = name + generateRandomSentence(6);
        String museumEditedNotificationMessage = "Обновлен музей: " + name;

        page(MuseumPage.class)
                .open(String.valueOf(museum.getId()))
                .clickEditBtn()
                .enterValues(name, "Russia", "Moscow", imgPath, description)
                .clickSaveBtn()
                .checkNotificationMessage(museumEditedNotificationMessage, MuseumPage.class)
                .checkMuseum(name, description, "Moscow", "Russia")
                .checkUrl(String.valueOf(museum.getId()));
    }

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе добавления музея")
    void checkNewMuseumPopupCloseBtnTest() {
        page(MuseumsPage.class)
                .open()
                .clickAddMuseumBtn()
                .enterValues(name, "Russia", "Moscow", imgPath, description)
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible(MuseumsPage.class);
    }

    @Museum
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе редактирования музея")
    void checkEditMuseumPopupCloseBtnTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum) {
        page(MuseumPage.class)
                .open(String.valueOf(museum.getId()))
                .clickEditBtn()
                .enterValues(name, "Russia", "Moscow", imgPath, description)
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible(MuseumPage.class);
    }
}
