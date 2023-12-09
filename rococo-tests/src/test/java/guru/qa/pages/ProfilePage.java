package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class ProfilePage extends BasePage<ProfilePage>{
    @FindBy(css = "[class*=variant-ghost]")
    @As("Кнопка Выйти")
    private SelenideElement logoutBtn;
}
