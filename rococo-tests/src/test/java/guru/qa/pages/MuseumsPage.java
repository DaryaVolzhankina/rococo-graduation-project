package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.NewMuseumPopup;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class MuseumsPage extends BaseFrontPage<MuseumsPage> {
    public static final String URL = CFG.rococoFrontUrl() + "/museum";

    @FindBy(css = "h2[class*=text]")
    @As("Заголовок Музеи")
    private SelenideElement museumsHeader;

    @FindBy(css = "[class=input]")
    @As("Строка поиска")
    private SelenideElement searchBar;

    @FindBy(xpath = "//img[@alt= 'Иконка поиска']/..")
    @As("Кнопка поиска")
    private SelenideElement searchBtn;

    @FindBy(css = "#page-content  ul li a div:first-of-type")
    @As("Список названий музеев на страницe")
    private ElementsCollection museumsTitlesList;

    @FindBy(css = "#page-content  ul li a")
    @As("Список музеев на страницe")
    private ElementsCollection museumsList;

    @FindBy(xpath = "//button[contains(text(), 'Добавить')]")
    @As("Кнопка Добавить музей")
    private SelenideElement addMuseumBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement museumAddedNotification;

    @Step("Открыть страницу музеев")
    public MuseumsPage open() {
        return Selenide.open(URL, MuseumsPage.class);
    }

    @Step("Проверить, открыта ли страница Музеи")
    public MuseumsPage checkPageIsOpened() {
        museumsHeader
                .shouldBe(visible)
                .shouldHave(text("Музеи"));
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

    @Step("Поиск музея с названием {0}")
    public MuseumsPage searchMuseum(String museumTitle) {
        searchBar.click();
        searchBar.setValue(museumTitle);
        searchBtn.click();
        return this;
    }

    @Step("Проверка списка музеев на наличие музея с названием {0}")
    public MuseumsPage checkMuseumsNamesList(String museumName) {
        museumsTitlesList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        museumsTitlesList.forEach(artist -> artist.shouldHave(Condition.partialText(museumName)));
        return this;
    }

    @Step("Проверка наличия списка музеев")
    public MuseumsPage checkMuseumsNamesList() {
        museumsTitlesList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        museumsTitlesList.get(0).shouldBe(visible);
        return this;
    }

    @Step("Открыть страницу музея")
    public MuseumPage getMuseum() {
        museumsList.shouldHave(CollectionCondition.size(1));
        museumsList.get(0).click();
        return page(MuseumPage.class);
    }

    @Step("Нажать на кнопку Добавить музей")
    public NewMuseumPopup clickAddMuseumBtn() {
        addMuseumBtn.click();
        return page(NewMuseumPopup.class);
    }
}
