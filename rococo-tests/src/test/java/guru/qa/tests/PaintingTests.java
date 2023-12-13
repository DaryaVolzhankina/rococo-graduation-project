package guru.qa.tests;

import com.github.javafaker.Faker;
import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.db.model.museum.MuseumEntity;
import guru.qa.db.model.painting.PaintingEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.Artist;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.jupiter.annotation.GeneratedArtist;
import guru.qa.jupiter.annotation.GeneratedMuseum;
import guru.qa.jupiter.annotation.Museum;
import guru.qa.jupiter.annotation.Painting;
import guru.qa.pages.ArtistPage;
import guru.qa.pages.PaintingPage;
import guru.qa.pages.PaintingsPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;
import static guru.qa.utils.FakerUtils.generateRandomSentence;

@Epic("Картины")
@DisplayName("Страница Картины")
public class PaintingTests extends BaseWebTest {

    public static final String title = new Faker().animal().name();
    public static final String description = title + generateRandomSentence(6);
    public static final String imgPath = "images/scream.jpg";
    public static final String paintingAddedNotificationMessage = "Добавлена картина: " + title;

    @Painting
    @Test
    @DisplayName("Поиск картины")
    void searchPaintingTest(@Painting PaintingEntity painting) {
        page(PaintingsPage.class)
                .open()
                .searchPainting(painting.getTitle())
                .checkPaintingsNamesList(painting.getTitle());
    }

    @DBUser
    @ApiLogin
    @Museum
    @Artist
    @Test
    @DisplayName("Добавить картину через страницу Картины")
    void addPaintingTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum,
                         @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(PaintingsPage.class)
                .open()
                .clickAddPaintingBtn()
                .enterValues(title, imgPath, artist.getName(), description, museum.getTitle())
                .clickAddBtn();
        page(PaintingsPage.class)
                .checkNotificationMessage(paintingAddedNotificationMessage)
                .searchPainting(title)
                .getPainting()
                .checkPainting(title, description, artist.getName());
    }

    @DBUser
    @ApiLogin
    @Museum
    @Artist
    @Test
    @DisplayName("Добавить картину через страницу автора")
    void addPaintingInArtistPageTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum,
                                     @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(ArtistPage.class)
                .open(String.valueOf(artist.getId()))
                .clickAddPaintingBtn()
                .enterValues(title, imgPath, description, museum.getTitle())
                .clickAddBtn();
        page(ArtistPage.class)
                .checkNotificationMessage(paintingAddedNotificationMessage)
                .checkPainting(title);
    }

    @Museum
    @Artist
    @Painting
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Редактировать картину")
    void editPaintingTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum,
                          @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist,
                          @Painting PaintingEntity paintingEntity) {
        String title = new Faker().animal().name();
        String description = title  + generateRandomSentence(6);
        String paintingEditedNotificationMessage = "Обновлена картина: " + title;

        page(PaintingPage.class)
                .open(String.valueOf(paintingEntity.getId()))
                .clickEditBtn()
                .enterValues(title, imgPath, artist.getName(), description, museum.getTitle())
                .clickSaveBtn()
                .checkNotificationMessage(paintingEditedNotificationMessage)
                .checkPainting(title, description, artist.getName())
                .checkUrl(String.valueOf(paintingEntity.getId()));
    }

    @DBUser
    @ApiLogin
    @Museum
    @Artist
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе новой картины")
    void checkNewPaintingPopupCloseBtnTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity museum,
                                           @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist) {
        page(PaintingsPage.class)
                .open()
                .clickAddPaintingBtn()
                .enterValues(title, imgPath, artist.getName(), description, museum.getTitle())
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible();
    }

    @Museum
    @Artist
    @Painting
    @DBUser
    @ApiLogin
    @Test
    @DisplayName("Проверить кнопку Закрыть в попапе редактирования картины")
    void checkEditPaintingPopupCloseBtnTest(@GeneratedMuseum(museumSelector = GeneratedMuseum.MuseumSelector.OUTER) MuseumEntity outerMuseum,
                                            @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity outerArtist,
                                            @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.NESTED) ArtistEntity nestedArtist,
                                            @Painting PaintingEntity paintingEntity) {
        page(PaintingPage.class)
                .open(String.valueOf(paintingEntity.getId()))
                .clickEditBtn()
                .enterValues(title, imgPath, outerArtist.getName(), description, outerMuseum.getTitle())
                .clickCloseBtn()
                .notificationMessageShouldNotBeVisible()
                .checkPainting(paintingEntity.getTitle(), paintingEntity.getDescription(), nestedArtist.getName());
    }
}
