package guru.qa.pages.elements;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class Header {

    @FindBy(css = "[class*= variant-filled-primary]")
    @As("Кнопка Войти")
    private SelenideElement loginButton;

    @FindBy(css = "[class*=btn-icon]")
    @As("Иконка профиля")
    private SelenideElement profileIcon;

    @FindBy(css = "[href='/']")
    @As("Логотип")
    private SelenideElement logo;

    @FindBy(css = "#shell-header [href='/painting']")
    @As("Кнопка Картины")
    private SelenideElement paintings;

    @FindBy(css = "#shell-header [href='/artist']")
    @As("Кнопка Художники}")
    private SelenideElement artists;

    @FindBy(css = "#shell-header [href='/museum']")
    @As("Кнопка Музеи")
    private SelenideElement museums;

    @FindBy(css = "[role=switch]")
    @As("Переключатель темы")
    private SelenideElement modeSwitch;
}
