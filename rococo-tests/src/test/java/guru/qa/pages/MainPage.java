package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class MainPage extends BasePage<MainPage>{

    @FindBy(css = "[class*= variant-filled-primary]")
    @As("Кнопка Войти")
    private SelenideElement loginButton;

    @FindBy(css = "[class*=btn-icon]")
    @As("Иконка профиля")
    private SelenideElement profileIcon;
}
