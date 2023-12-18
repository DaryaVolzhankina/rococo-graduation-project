package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.elements.EditArtistPopup;
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
public class ArtistPage extends BaseFrontPage<ArtistPage> {

    @FindBy(css = "#page-content header")
    @As("Заголовок Имя художника")
    private SelenideElement artistName;

    @FindBy(css = "#page-content section p[class*=col-span]")
    @As("Биография")
    private SelenideElement biography;

    @FindBy(css = "[data-testid=edit-artist]")
    @As("Кнопка Редактировать")
    private SelenideElement editBtn;

    @FindBy(xpath = "(//button[contains(text(), 'Добавить')])[1]")
    @As("Кнопка Добавить картину")
    private SelenideElement addPaintingBtn;

    @FindBy(css = "[class*='pointer-events-auto']")
    @As("Уведомление")
    private SelenideElement notification;

    @FindBy(css = "#page-content  ul li a div")
    @As("Список названий картин")
    private ElementsCollection paintingsTitlesList;

    @Step("Открыть страницу художникa c id {0}")
    public ArtistPage open(String artistId) {
        return Selenide.open(CFG.rococoFrontUrl() + "/artist/" + artistId, ArtistPage.class);
    }

    @Step("Проверить имя и биографию художника")
    public ArtistPage checkArtist(String name, String biography) {
        artistName.shouldHave(Condition.text(name));
        this.biography.shouldHave(Condition.text(biography));
        return this;
    }

    @Step("Кликнуть на кнопку Редактировать")
    public EditArtistPopup clickEditBtn() {
        editBtn.click();
        return page(EditArtistPopup.class);
    }

    @Step("Проверить, что ссылка содержит id артиста")
    public ArtistPage checkUrl(String artistId) {
        assertThat(webdriver().driver().url())
                .isEqualTo(CFG.rococoFrontUrl() + "/artist/" + artistId);
        return this;
    }

    @Step("Нажать на кнопку Добавить картину")
    public NewPaintingPopup clickAddPaintingBtn() {
        addPaintingBtn.click();
        return page(NewPaintingPopup.class);
    }

    @Step("Проверить наличие картины в списке картин")
    public ArtistPage checkPainting(String paintingTitle) {
        paintingsTitlesList.shouldHave(CollectionCondition.size(1));
        paintingsTitlesList.first()
                .shouldHave(text(paintingTitle));
        return this;
    }
}
