package guru.qa.tests;

import guru.qa.pages.MainPage;
import guru.qa.pages.PaintingsPage;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.page;

@Epic("Заголовок")
@DisplayName("Элементы Заголовка")
public class HeaderElementsTests extends BaseWebTest{

    @Test
    @DisplayName("Проверка переключателя световой темы")
    void loginBtnShouldNotBeVisibleAfterAuthorization() {
        page(MainPage.class).open()
                .clickModeSwitch()
                .checkDarkMode()
                .clickModeSwitch()
                .checkLiteMode();
    }

    @Test
    @DisplayName("Проверка кнопок в хедере")
    void checkHeadersButtons() {
        page(MainPage.class).open()
                .clickPaintings()
                .checkPageIsOpened()
                .clickArtists()
                .checkPageIsOpened()
                .clickMuseums()
                .checkPageIsOpened();
    }

    @Test
    @DisplayName("Проверка логотипа")
    void checkLogo() {
        page(PaintingsPage.class).open()
                .clickLogo()
                .checkPageIsOpened();
    }
}
