package guru.qa.pages.elements;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import guru.qa.pages.BaseFrontPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class ProfilePopup {
    @FindBy(css = "[class*=variant-ghost]")
    @As("Кнопка Выйти")
    private SelenideElement logoutBtn;

    @FindBy(css = "[name=content]")
    @As("Поле Обновить фото профиля")
    private SelenideElement photoField;

    @FindBy(css = "form figure img")
    @As("Фото профиля")
    private SelenideElement avatar;

    @FindBy(css = "[name=firstname]")
    @As("Поле Имя")
    private SelenideElement firstnameField;

    @FindBy(css = "[name=surname]")
    @As("Поле Фамилия")
    private SelenideElement surnameField;

    @FindBy(css = "div[class*=card] [type='submit']")
    @As("Кнопка Обновить профиль")
    private SelenideElement updateProfileBtn;

    @FindBy(css = "div[class*=card] [type='button']")
    @As("Кнопка Закрыть")
    private SelenideElement closeBtn;

    @Step("Кликнуть на кнопку Выйти")
    public <T extends BaseFrontPage<T>> T clickLogoutBtn(Class<T> page) {
        logoutBtn.shouldBe(visible).click();
        return page(page);
    }

    @Step("Ввести данные в поля")
    public ProfilePopup enterValues(String name, String surname, String photoPath) {
        firstnameField.setValue(name);
        surnameField.setValue(surname);
        photoField.uploadFromClasspath(photoPath);
        return this;
    }

    @Step("Нажать на кнопку сохранить")
    public void clickUpdateBtn() {
        updateProfileBtn.click();
    }

    @Step("Нажать на кнопку Закрыть")
    public void clickCloseBtn() {
        updateProfileBtn.click();
    }
}
