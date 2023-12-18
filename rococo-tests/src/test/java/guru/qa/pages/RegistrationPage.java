package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.webdriver;
import static guru.qa.utils.FakerUtils.generateRandomPassword;
import static guru.qa.utils.FakerUtils.generateRandomUsername;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class RegistrationPage extends BaseAuthPage<RegistrationPage> {

    public static final String URL = CFG.rococoAuthUrl() + "/register";

    @FindBy(css = "[name=passwordSubmit]")
    @As("Поле Повторите пароль")
    private SelenideElement passwordSubmitField;

    @FindBy(css = "a[class*=form__submit]")
    @As("Кнопка Войти в систему")
    private SelenideElement enterSystemBtn;

    @FindBy(css = "a[class*=form__link]")
    @As("Кнопка Войти")
    private SelenideElement loginBtn;

    @Step("Открыть страницу регистрации")
    public RegistrationPage open() {
        return Selenide.open(URL, RegistrationPage.class);
    }

    @Step("Проверить, открыта ли страница регистрации")
    public RegistrationPage checkPageIsOpened() {
        passwordSubmitField
                .shouldBe(visible);
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

    @Step("Заполнить поля Имя пользователя и поля паролей")
    public RegistrationPage enterCredentials(String username, String password) {
        getUsernameField().setValue(username);
        getPasswordField().setValue(password);
        getPasswordSubmitField().setValue(password);
        return this;
    }

    @Step("Заполнить поля Имя пользователя и поля паролей")
    public RegistrationPage enterRandomCredentials() {
        getUsernameField().setValue(generateRandomUsername());
        getPasswordField().setValue(generateRandomPassword());
        getPasswordSubmitField().setValue(generateRandomPassword());
        return this;
    }

    @Step("Заполнить поля Имя пользователя и пароль")
    public RegistrationPage enterCredentialsWithoutPasswordSubmitField(String username, String password) {
        getUsernameField().setValue(username);
        getPasswordField().setValue(password);
        return this;
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    public RegistrationPage clickRegisterBtn() {
        getSubmitBtn().click();
        return this;
    }

    @Step("Нажать на кнопку Войти в систему")
    public LoginPage clickEnterSystemBtn() {
        enterSystemBtn.click();
        return page(LoginPage.class);
    }

    @Step("Нажать на кнопку Войти в систему")
    public LoginPage clickLoginBtn() {
        loginBtn.click();
        return page(LoginPage.class);
    }
}
