package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

@Getter
public class RegistrationPage extends BaseAuthPage<RegistrationPage> {

    public static final String URL = CFG.rococoAuthUrl() + "/register";

    @FindBy(css = "[name=passwordSubmit]")
    @As("Поле Повторите пароль")
    private SelenideElement passwordSubmitField;

    @FindBy(css = "a[class*=form__submit]")
    @As("Кнопка Войти в систему")
    private SelenideElement loginBtn;

    @Step("Заполнить поля Имя пользователя и поля паролей")
    public RegistrationPage enterCredentials(String username, String password) {
        getUsernameField().setValue(username);
        getPasswordField().setValue(password);
        getPasswordSubmitField().setValue(password);
        return this;
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    public RegistrationPage clickRegisterBtn() {
        getSubmitBtn().click();
        return this;
    }

    @Step("Нажать на кнопку Войти в систему")
    public LoginPage clickLoginBtn() {
        loginBtn.click();
        return page(LoginPage.class);
    }
}
