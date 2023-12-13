package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.NewArtistPopup;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class ArtistsPage extends BaseFrontPage<ArtistsPage> {
    public static final String URL = CFG.rococoFrontUrl() + "/artist";

    @FindBy(css = "h2[class*=text]")
    @As("Заголовок Художники")
    private SelenideElement artistsHeader;
    @FindBy(css = "[class=input]")
    @As("Строка поиска")
    private SelenideElement searchBar;

    @FindBy(xpath = "//img[@alt= 'Иконка поиска']/..")
    @As("Кнопка поиска")
    private SelenideElement searchBtn;

    @FindBy(css = "#page-content  ul li a span")
    @As("Список имен художников на страницe")
    private ElementsCollection artistsNamesList;

    @FindBy(css = "#page-content  ul li a")
    @As("Список художников на страницe")
    private ElementsCollection artistsList;

    @FindBy(xpath = "//button[contains(text(), 'Добавить')]")
    @As("Кнопка Добавить художника")
    private SelenideElement addArtistBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement artistAddedNotification;

    @Step("Открыть страницу художников")
    public ArtistsPage open() {
        return Selenide.open(URL, ArtistsPage.class);
    }

    @Step("Проверить, открыта ли страница Художники")
    public ArtistsPage checkPageIsOpened() {
        artistsHeader
                .shouldBe(visible)
                .shouldHave(text("Художники"));
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

    @Step("Поиск художника с именем {0}")
    public ArtistsPage searchArtist(String artistName) {
        searchBar.click();
        searchBar.setValue(artistName);
        searchBtn.click();
        return this;
    }

    @Step("Проверка списка художников")
    public ArtistsPage checkArtistsNamesList(String artistName) {
        artistsNamesList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        artistsNamesList.forEach(artist -> artist.shouldHave(Condition.partialText(artistName)));
        return this;
    }

    @Step("Проверка художникa")
    public ArtistPage getArtist() {
        artistsList.shouldHave(CollectionCondition.size(1));
        artistsList.get(0).click();
        return page(ArtistPage.class);
    }

    @Step("Нажать на кнопку Добавить художника")
    public NewArtistPopup clickAddArtistsBtn() {
        addArtistBtn.click();
        return page(NewArtistPopup.class);
    }

    @Step("Проверить текст сообщения")
    public ArtistsPage checkNotificationMessage(String text) {
        artistAddedNotification
                .shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    @Step("Проверить, что на странице нет никакого информационного сообщения")
    public ArtistsPage notificationMessageShouldNotBeVisible() {
        artistAddedNotification
                .shouldNotBe(visible);
        return this;
    }
}
