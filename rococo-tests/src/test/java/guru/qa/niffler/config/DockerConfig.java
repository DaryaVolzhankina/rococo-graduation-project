package guru.qa.niffler.config;

import com.codeborne.selenide.Configuration;

public class DockerConfig implements Config {

    static final DockerConfig config = new DockerConfig();

    static {
        Configuration.remote = "http://localhost:4444/wd";
    }

    private DockerConfig() {
    }

    @Override
    public String databaseHost() {
        return "niffler-all-db";
    }
    @Override
    public String rococoFrontUrl() {
        return "http://frontend.niffler.dc";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://auth.niffler.dc";
    }
}
