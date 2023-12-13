package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.EditMuseumPopup;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class MuseumPage extends BaseFrontPage<MuseumPage> {

    @FindBy(css = "#page-content header")
    @As("Название музея")
    private SelenideElement museumTitle;

    @FindBy(css = "#page-content header +div")
    @As("Местоположение музея")
    private SelenideElement location;

    @FindBy(css = "#page-content header +div +div +div")
    @As("Описание музея")
    private SelenideElement description;

    @FindBy(css = "[data-testid=edit-museum]")
    @As("Кнопка Редактировать")
    private SelenideElement editBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement museumEditedNotification;

    @Step("Открыть страницу музея с id {0}")
    public MuseumPage open(String museumId) {
        return Selenide.open(CFG.rococoFrontUrl() + "/museum/" + museumId, MuseumPage.class);
    }

    @Step("Проверить заголовок и описание музея")
    public MuseumPage checkMuseum(String title, String description, String city, String country) {
        museumTitle.shouldHave(Condition.text(title));
        this.description.shouldHave(Condition.text(description));
        location.shouldHave(Condition.text(country + ", " + city));
        return this;
    }

    @Step("Кликнуть на кнопку Редактировать")
    public EditMuseumPopup clickEditBtn() {
        editBtn.click();
        return page(EditMuseumPopup.class);
    }

    @Step("Проверить, что ссылка имеет внутри себя id музея")
    public MuseumPage checkUrl(String museumId) {
        assertThat(webdriver().driver().url())
                .isEqualTo(CFG.rococoFrontUrl() + "/museum/" + museumId);
        return this;
    }

    @Step("Проверить текст сообщения")
    public MuseumPage checkNotificationMessage(String text) {
        museumEditedNotification
                .shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    @Step("Проверить, что на странице нет никакого информационного сообщения")
    public MuseumPage notificationMessageShouldNotBeVisible() {
        museumEditedNotification
                .shouldNotBe(visible);
        return this;
    }
}
