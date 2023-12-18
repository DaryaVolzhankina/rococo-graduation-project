package guru.qa.tests;

import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.Artist;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.jupiter.annotation.GeneratedArtist;
import guru.qa.pages.ArtistPage;
import guru.qa.pages.ArtistsPage;
import guru.qa.pages.MainPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomSentence;
import static guru.qa.utils.FakerUtils.generateRandomUsername;

@Epic("Художники")
@DisplayName("Страница Художники")
public class ArtistTests extends BaseWebTest {

    public static final String name = generateRandomUsername();
    public static final String biography = name  + generateRandomSentence(6);
    public static final String imgPath = "images/artist.jpg";
    public static final String artistAddedNotificationMessage = "Добавлен художник: " + name;

    @Artist
    @Test
    @DisplayName("Переход с главной страницы на страницу Художники")
    void checkArtistsPageTest(@GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(MainPage.class)
                .open()
                .clickArtistsBtn()
                .checkPageIsOpened()
                .checkArtistsNamesList();
    }

    @Artist
    @Test
    @DisplayName("Поиск художника")
    void searchArtistTest(@GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(ArtistsPage.class)
                .open()
                .searchArtist(artist.getName())
                .checkArtistsNamesList(artist.getName());
    }

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверка добавления художника")
    void addArtistTest() {
        page(ArtistsPage.class)
                .open()
                .clickAddArtistsBtn()
                .enterValues(name, imgPath, biography)
                .clickAddArtistBtn()
                .checkNotificationMessage(artistAddedNotificationMessage, ArtistsPage.class)
                .searchArtist(name)
                .getArtist()
                .checkArtist(name, biography);
    }

    @Artist
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверка редактирования художника")
    void editArtistTest(@GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        String name = generateRandomUsername();
        String biography = name  + generateRandomSentence(6);
        String artistEditedNotificationMessage = "Обновлен художник: " + name;

        page(ArtistPage.class)
                .open(String.valueOf(artist.getId()))
                .clickEditBtn()
                .enterValues(name, imgPath, biography)
                .clickSaveBtn()
                .checkNotificationMessage(artistEditedNotificationMessage, ArtistPage.class)
                .checkArtist(name, biography)
                .checkUrl(String.valueOf(artist.getId()));
    }

    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе Добавить художника")
    void checkNewArtistPopupCloseBtnTest() {
        page(ArtistsPage.class)
                .open()
                .clickAddArtistsBtn()
                .enterValues(name, imgPath, biography)
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible(ArtistPage.class);
    }

    @Artist
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе Редактировать художника")
    void checkEditArtistPopupCloseBtnTest(@GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(ArtistPage.class)
                .open(String.valueOf(artist.getId()))
                .clickEditBtn()
                .enterValues(name, imgPath, biography)
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible(ArtistPage.class);
    }
}
