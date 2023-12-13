package guru.qa.pages;

import com.codeborne.selenide.As;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class MainPage extends BaseFrontPage<MainPage> {
    public static final String URL = CFG.rococoFrontUrl() + "/";

    @FindBy(css = "p[class*=text-center]")
    @As("Заголовок главной страницы")
    private SelenideElement title;

    @FindBy(css = "#page-content  [href='/artist']")
    @As("Раздел Художники")
    private SelenideElement artistsButton;

    @FindBy(css = "#page-content  [href='/painting']")
    @As("Раздел Картины")
    private SelenideElement paintingsButton;

    @FindBy(css = "#page-content  [href='/museum']")
    @As("Раздел Музей")
    private SelenideElement museumsButton;

    @Step("Открыть главную страницу")
    public MainPage open() {
        return Selenide.open(URL, MainPage.class);
    }

    @Step("Проверить, открыта ли главная страница")
    public MainPage checkPageIsOpened() {
        title
                .shouldBe(visible)
                .shouldHave(text("Ваши любимые картины и художники всегда рядом"));
        assertThat(webdriver().driver().url())
                .isEqualTo(URL);
        return this;
    }

}
