package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.NewPaintingPopup;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class PaintingsPage extends BaseFrontPage<PaintingsPage> {
    public static final String URL = CFG.rococoFrontUrl() + "/painting";

    @FindBy(css = "h2[class*=text]")
    @As("Заголовок Картины")
    private SelenideElement paintingsHeader;

    @FindBy(css = "[class=input]")
    @As("Строка поиска")
    private SelenideElement searchBar;

    @FindBy(xpath = "//img[@alt= 'Иконка поиска']/..")
    @As("Кнопка поиска")
    private SelenideElement searchBtn;

    @FindBy(css = "#page-content  ul li a div:first-of-type")
    @As("Список названий картин")
    private ElementsCollection paintingsTitlesList;

    @FindBy(css = "#page-content  ul li a")
    @As("Список картин")
    private ElementsCollection paintingsList;

    @FindBy(xpath = "//button[contains(text(), 'Добавить')]")
    @As("Кнопка Добавить картину")
    private SelenideElement addPaintingBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement paintingAddedNotification;

    @Step("Открыть страницу Картины")
    public PaintingsPage open() {
        return Selenide.open(URL, PaintingsPage.class);
    }

    @Step("Проверить, открыта ли страница Картины")
    public PaintingsPage checkPageIsOpened() {
        paintingsHeader
                .shouldBe(visible)
                .shouldHave(text("Картины"));
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

    @Step("Поиск картины с названием {0}")
    public PaintingsPage searchPainting(String paintingTitle) {
        searchBar.click();
        searchBar.setValue(paintingTitle);
        searchBtn.click();
        return this;
    }

    @Step("Проверка списка картин на наличие картины с названием {0}")
    public PaintingsPage checkPaintingsNamesList(String paintingName) {
        paintingsTitlesList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        paintingsTitlesList.forEach(artist -> artist.shouldHave(Condition.partialText(paintingName)));
        return this;
    }

    @Step("Проверка наличия списка картин")
    public PaintingsPage checkPaintingsNamesList() {
        paintingsTitlesList.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        paintingsTitlesList.get(0).shouldBe(visible);
        return this;
    }

    @Step("Открыть страницу картины")
    public PaintingPage getPainting() {
        paintingsList.shouldHave(CollectionCondition.size(1));
        paintingsList.get(0).click();
        return page(PaintingPage.class);
    }

    @Step("Нажать на кнопку Добавить картину")
    public NewPaintingPopup clickAddPaintingBtn() {
        addPaintingBtn.click();
        return page(NewPaintingPopup.class);
    }
}
