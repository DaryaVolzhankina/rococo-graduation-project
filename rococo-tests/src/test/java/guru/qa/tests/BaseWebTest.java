package guru.qa.tests;

import guru.qa.config.Config;
import guru.qa.jupiter.annotation.WebTest;

@WebTest
public abstract class BaseWebTest {
    protected static final Config CFG = Config.getInstance();
}
