package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.EditPaintingPopup;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class PaintingPage extends BaseFrontPage<PaintingPage> {

    @FindBy(css = "#page-content header")
    @As("Название картины")
    private SelenideElement paintingTitle;

    @FindBy(css = "#page-content header +div")
    @As("Художник")
    private SelenideElement artist;

    @FindBy(css = "#page-content img")
    @As("Изображение картины")
    private SelenideElement painting;
    @FindBy(css = "#page-content div[class='m-4']")
    @As("Описание картины")
    private SelenideElement description;

    @FindBy(css = "[data-testid=edit-painting]")
    @As("Кнопка Редактировать")
    private SelenideElement editBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement paintingEditedNotification;

    @Step("Открыть страницу картины с id {0}")
    public PaintingPage open(String paintingId) {
        return Selenide.open(CFG.rococoFrontUrl() + "/painting/" + paintingId, PaintingPage.class);
    }

    @Step("Проверить заголовок и описание картины")
    public PaintingPage checkPainting(String title, String description, String artistName) {
        paintingTitle.shouldHave(Condition.text(title));
        this.description.shouldHave(Condition.text(description));
        artist.shouldHave(text(artistName));
        painting.shouldHave(Condition.attribute("src"));
        return this;
    }

    @Step("Кликнуть на кнопку Редактировать")
    public EditPaintingPopup clickEditBtn() {
        editBtn.click();
        return page(EditPaintingPopup.class);
    }

    @Step("Проверить, что ссылка имеет внутри себя id картины")
    public PaintingPage checkUrl(String museumId) {
        assertThat(webdriver().driver().url())
                .isEqualTo(CFG.rococoFrontUrl() + "/painting/" + museumId);
        return this;
    }

    @Step("Проверить текст сообщения")
    public PaintingPage checkNotificationMessage(String text) {
        paintingEditedNotification
                .shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    @Step("Проверить, что на странице нет никакого информационного сообщения")
    public PaintingPage notificationMessageShouldNotBeVisible() {
        paintingEditedNotification
                .shouldNotBe(visible);
        return this;
    }
}
