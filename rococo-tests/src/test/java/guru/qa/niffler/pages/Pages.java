package guru.qa.niffler.pages;

import static com.codeborne.selenide.Selenide.page;

public class Pages {

    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private RegistrationPage registrationPage;

    public <PageObjectClass> PageObjectClass getPage(Class<PageObjectClass> pageObjectClass) {
        return page(pageObjectClass);
    }

    public MainPage mainPage() {
        return mainPage = (mainPage == null ? page(MainPage.class) : mainPage);
    }

    public LoginPage loginPage() {
        return loginPage = (loginPage == null ? page(LoginPage.class) : loginPage);
    }

    public ProfilePage profilePage() {
        return profilePage = (profilePage == null ? page(ProfilePage.class) : profilePage);
    }

    public RegistrationPage registrationPage() {
        return registrationPage = (registrationPage == null ? page(RegistrationPage.class) : registrationPage);
    }
}
