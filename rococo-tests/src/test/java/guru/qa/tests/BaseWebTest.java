package guru.qa.tests;

import guru.qa.config.Config;
import guru.qa.jupiter.annotation.WebTest;
import guru.qa.pages.Pages;
import org.junit.jupiter.api.BeforeEach;

@WebTest
public abstract class BaseWebTest {
    protected Pages pages;
    protected static final Config CFG = Config.getInstance();

    @BeforeEach
    public void setupTest() {
        pages = new Pages();
    }
}
