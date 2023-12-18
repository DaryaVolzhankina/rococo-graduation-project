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
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class LoginPage extends BaseAuthPage<LoginPage> {

    public static final String URL = CFG.rococoAuthUrl() + "/login";

    @FindBy(css = "[href='/register']")
    @As("Кнопка Зарегистрироваться")
    private SelenideElement registerBtn;

    @Step("Открыть страницу авторизации")
    public LoginPage open() {
        return Selenide.open(URL, LoginPage.class);
    }

    @Step("Проверить, открыта ли страница авторизации")
    public LoginPage checkPageIsOpened() {
        registerBtn
                .shouldBe(visible);
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

    @Step("Заполнить поля Имя пользователя и Пароль данными")
    public LoginPage enterCredentials(String username, String password) {
        getUsernameField().setValue(username);
        getPasswordField().setValue(password);
        return this;
    }

    @Step("Кликнуть на кнопку Войти")
    public void clickLoginButton() {
        getSubmitBtn().click();
    }

    @Step("Кликнуть на кнопку Зарегистрироваться")
    public RegistrationPage clickRegisterButton() {
        registerBtn.click();
        return page(RegistrationPage.class);
    }
}
