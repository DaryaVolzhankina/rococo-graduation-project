package guru.qa.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.db.model.painting.PaintingEntity;
import guru.qa.jupiter.annotation.ApiLogin;
import guru.qa.jupiter.annotation.Artist;
import guru.qa.jupiter.annotation.DBUser;
import guru.qa.jupiter.annotation.GeneratedArtist;
import guru.qa.jupiter.annotation.Museum;
import guru.qa.jupiter.annotation.Painting;
import org.junit.jupiter.api.Test;

public class LogoutTests extends BaseWebTest {

    @DBUser
    @ApiLogin
    @Painting(museum = @Museum)
    @Artist
    @Test
    void logoutBtnShouldBeVisibleAfterLogout(AuthUserEntity user, @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.OUTER) ArtistEntity artist2, @GeneratedArtist(artistSelector = GeneratedArtist.ArtistSelector.NESTED) ArtistEntity artist ,@Painting PaintingEntity paintingEntity) {
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
