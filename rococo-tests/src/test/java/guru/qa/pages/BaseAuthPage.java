package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import guru.qa.config.Config;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

@Getter
public abstract class BaseAuthPage<T extends BaseAuthPage<T>> {
    protected static final Config CFG = Config.getInstance();

    @FindBy(css = "[name=username]")
    @As("Поле Имя Пользователя")
    private SelenideElement usernameField;

    @FindBy(css = "[name=password]")
    @As("Поле Пароль")
    private SelenideElement passwordField;

    @FindBy(css = "[type=submit]")
    @As("Кнопка Войти/Зарегистрироваться")
    private SelenideElement submitBtn;

    @FindBy(css = "[class=form__error]")
    @As("Сообщение об ошибке")
    private SelenideElement errorNotification;

    @Step("Проверить, что сообщение об ошибке имеет текст {0}")
    public  <T extends BaseAuthPage<T>> T checkErrorNotificationMessage(String text, Class<T> page) {
        errorNotification
                .shouldBe(visible)
                .shouldHave(text(text));
        return page(page);
    }
}
