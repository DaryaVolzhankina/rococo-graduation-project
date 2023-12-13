package guru.qa.pages.elements;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.ArtistsPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class NewArtistPopup {
    @FindBy(css = "[name=name]")
    @As("Поле Имя")
    private SelenideElement nameField;

    @FindBy(css = "[name=photo]")
    @As("Поле Изображение художника")
    private SelenideElement photoField;

    @FindBy(css = "[name=biography]")
    @As("Поле Биография")
    private SelenideElement biographyField;

    @FindBy(css = "div[class*=card] [type='submit']")
    @As("Кнопка Добавить")
    private SelenideElement addBtn;

    @FindBy(css = "div[class*=card] [type='button']")
    @As("Кнопка Закрыть")
    private SelenideElement closeBtn;

    @Step("Ввести данные в поля")
    public NewArtistPopup enterValues(String artistName, String photoPath, String biography) {
        nameField.setValue(artistName);
        photoField.uploadFromClasspath(photoPath);
        biographyField.setValue(biography);
        return this;
    }

    @Step("Нажать на кнопку Добавить")
    public ArtistsPage clickAddArtistBtn() {
        addBtn.click();
        return page(ArtistsPage.class);
    }

    @Step("Нажать на кнопку Закрыть")
    public ArtistsPage clickCloseBtn() {
        closeBtn.click();
        photoField.shouldNotBe(visible);
        return page(ArtistsPage.class);
    }
}
