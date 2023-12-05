package guru.qa.niffler.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = "/login";

    @FindBy(css = "[name=username]")
    @As("Поле Имя Пользователя")
    private SelenideElement usernameField;

    @FindBy(css = "[name=password]")
    @As("Поле Пароль")
    private SelenideElement passwordField;

    @FindBy(css = "[type=submit]")
    @As("Кнопка Войти")
    private SelenideElement loginBtn;

    @FindBy(css = "[href='/register']")
    @As("Кнопка Зарегистрироваться")
    private SelenideElement registerBtn;

    @FindBy(css = "[class=form__error]")
    @As("Сообщение об ошибке")
    private SelenideElement errorNotification;
}
