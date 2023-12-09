package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class RegistrationPage extends BasePage<RegistrationPage>{

    public static final String URL = "/register";

    @FindBy(css = "[name=username]")
    @As("Поле Имя Пользователя")
    private SelenideElement usernameField;

    @FindBy(css = "[name=password]")
    @As("Поле Пароль")
    private SelenideElement passwordField;

    @FindBy(css = "[name=passwordSubmit]")
    @As("Поле Повторите пароль")
    private SelenideElement passwordSubmitField;

    @FindBy(css = "[type=submit]")
    @As("Кнопка Зарегистрироваться")
    private SelenideElement registerBtn;

    @FindBy(css = "a[class*=form__submit]")
    @As("Кнопка Войти в систему")
    private SelenideElement loginBtn;

    @FindBy(css = "[class=form__error]")
    @As("Сообщение об ошибке")
    private SelenideElement errorNotification;
}
