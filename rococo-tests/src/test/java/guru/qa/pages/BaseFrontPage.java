
package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.config.Config;
import guru.qa.pages.elements.Header;
import guru.qa.pages.elements.ProfilePopup;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public abstract class BaseFrontPage<T extends BaseFrontPage<T>> {
    protected static final Config CFG = Config.getInstance();

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement notification;

    @FindBy(css = "html[lang]")
    @As("html-тег,где хранятся все элементы страницы")
    private SelenideElement pageContainer;

    public Header getHeader() {
        return page(Header.class);
    }

    @Step("Кликнуть на кнопку Войти")
    public LoginPage clickLoginButton() {
        this.getHeader()
                .getLoginButton()
                .click();
        return page(LoginPage.class);
    }

    @Step("Проверить, что кнопка Войти не должна быть видима")
    public BaseFrontPage<T> loginButtonShouldNotBeVisible() {
        this.getHeader()
                .getLoginButton()
                .shouldNotBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что иконка профиля должна быть видима")
    public BaseFrontPage<T> profileIconShouldBeVisible() {
        this.getHeader()
                .getProfileIcon()
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что кнопка Войти должна быть видима")
    public BaseFrontPage<T> loginButtonShouldBeVisible() {
        this.getHeader()
                .getLoginButton()
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что иконка профиля не должна быть видима")
    public BaseFrontPage<T> profileIconShouldNotBeVisible() {
        this.getHeader()
                .getProfileIcon()
                .shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Кликнуть на иконку профиля")
    public ProfilePopup clickProfileIcon() {
        getHeader()
                .getProfileIcon()
                .click();
        return page(ProfilePopup.class);
    }

    @Step("Проверить текст сообщения")
    public BaseFrontPage<T> checkNotificationMessage(String text) {
        notification
                .shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    @Step("Проверить, что на странице нет никакого информационного сообщения")
    public BaseFrontPage<T> notificationMessageShouldNotBeVisible() {
        notification
                .shouldNotBe(visible);
        return this;
    }

    @Step("Кликнуть на переключатель тем")
    public BaseFrontPage<T> clickModeSwitch() {
        getHeader().getModeSwitch().click();
        return this;
    }

    @Step("Кликнуть на логотип")
    public MainPage clickLogo() {
        getHeader().getLogo().click();
        return page(MainPage.class);
    }

    @Step("Кликнуть на кнопку Картины")
    public PaintingsPage clickPaintings() {
        getHeader().getPaintings().click();
        return page(PaintingsPage.class);
    }

    @Step("Кликнуть на кнопку Художники")
    public ArtistsPage clickArtists() {
        getHeader().getArtists().click();
        return page(ArtistsPage.class);
    }

    @Step("Кликнуть на кнопку Музеи")
    public MuseumsPage clickMuseums() {
        getHeader().getMuseums().click();
        return page(MuseumsPage.class);
    }

    @Step("Проверить, что включен темный режим")
    public BaseFrontPage<T> checkDarkMode() {
        getHeader().getModeSwitch().shouldHave(attribute("aria-checked", "false"));
        pageContainer.shouldHave(attribute("class", "dark"));
        return this;
    }

    @Step("Проверить, что включен светлый режим")
    public BaseFrontPage<T> checkLiteMode() {
        getHeader().getModeSwitch().shouldHave(attribute("aria-checked", "true"));
        pageContainer.shouldNotHave(attribute("class", "dark"));
        return this;
    }
}
