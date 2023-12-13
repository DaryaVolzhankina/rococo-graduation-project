package guru.qa.pages.elements;

import com.codeborne.selenide.As;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.MuseumsPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class NewMuseumPopup {
    @FindBy(css = "[name=title]")
    @As("Поле Название музея")
    private SelenideElement museumTitleField;

    @FindBy(css = "[name=countryId]")
    @As("Поле Укажите страну")
    private SelenideElement chooseCountryField;

    @FindBy(css = "[name=countryId] option")
    @As("Список стран")
    private ElementsCollection countriesList;

    @FindBy(css = "[name=city]")
    @As("Поле Укажите город")
    private SelenideElement cityField;

    @FindBy(css = "[name=photo]")
    @As("Поле Изображение музея")
    private SelenideElement photoField;

    @FindBy(css = "[name=description]")
    @As("Поле О музее")
    private SelenideElement descriptionField;

    @FindBy(css = "div[class*=card] [type='submit']")
    @As("Кнопка Добавить")
    private SelenideElement addBtn;

    @FindBy(css = "div[class*=card] [type='button']")
    @As("Кнопка Закрыть")
    private SelenideElement closeBtn;

    @Step("Ввести данные в поля")
    public NewMuseumPopup enterValues(String museumTitle, String countryName, String city, String photoPath, String description) {
        museumTitleField.setValue(museumTitle);
        countriesList.last().scrollIntoView(true);
        chooseCountryField.selectOptionContainingText(countryName);
        cityField.setValue(city);
        photoField.uploadFromClasspath(photoPath);
        descriptionField.setValue(description);
        return this;
    }

    @Step("Нажать на кнопку Добавить")
    public MuseumsPage clickAddBtn() {
        addBtn.click();
        return page(MuseumsPage.class);
    }

    @Step("Нажать на кнопку Закрыть")
    public MuseumsPage clickCloseBtn() {
        closeBtn.click();
        photoField.shouldNotBe(visible);
        return page(MuseumsPage.class);
    }
}
