package guru.qa.pages.elements;

import com.codeborne.selenide.As;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.PaintingsPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class NewPaintingPopup {
    @FindBy(css = "[name=title]")
    @As("Поле Название картины")
    private SelenideElement paintingTitleField;

    @FindBy(css = "[name=authorId]")
    @As("Поле Укажите автора картины")
    private SelenideElement authorField;

    @FindBy(css = "[name=authorId] option")
    @As("Список художников")
    private ElementsCollection artistsList;

    @FindBy(css = "[name=museumId]")
    @As("Поле Укажите, где хранится оригинал")
    private SelenideElement museumField;

    @FindBy(css = "[name=museumId] option")
    @As("Список музеев")
    private ElementsCollection museumsList;

    @FindBy(css = "[name=content]")
    @As("Поле Загрузите изображеение картины")
    private SelenideElement photoField;

    @FindBy(css = "[name=description]")
    @As("Поле Описание картины")
    private SelenideElement descriptionField;

    @FindBy(css = "div[class*=card] [type='submit']")
    @As("Кнопка Добавить")
    private SelenideElement addBtn;

    @FindBy(css = "div[class*=card] [type='button']")
    @As("Кнопка Закрыть")
    private SelenideElement closeBtn;

    @Step("Ввести данные в поля")
    public NewPaintingPopup enterValues(String paintingTitle, String photoPath, String author, String description, String museumTitle) {
        paintingTitleField.setValue(paintingTitle);
        photoField.uploadFromClasspath(photoPath);
        artistsList.last().scrollIntoView(true);
        authorField.selectOptionContainingText(author);
        descriptionField.setValue(description);
        museumsList.last().scrollIntoView(true);
        museumField.selectOptionContainingText(museumTitle);
        return this;
    }

    @Step("Ввести данные в поля")
    public NewPaintingPopup enterValues(String paintingTitle, String photoPath, String description, String museumTitle) {
        paintingTitleField.setValue(paintingTitle);
        photoField.uploadFromClasspath(photoPath);;
        descriptionField.setValue(description);
        museumsList.last().scrollIntoView(true);
        museumField.selectOptionContainingText(museumTitle);
        return this;
    }

    @Step("Нажать на кнопку Добавить")
    public void clickAddBtn() {
        addBtn.click();
    }

    @Step("Нажать на кнопку Закрыть")
    public PaintingsPage clickCloseBtn() {
        closeBtn.click();
        photoField.shouldNotBe(visible);
        return page(PaintingsPage.class);
    }
}
