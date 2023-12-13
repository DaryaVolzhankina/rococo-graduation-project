package guru.qa.config;

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
        return "rococo-all-db";
    }
    @Override
    public String rococoFrontUrl() {
        return "http://frontend.rococo.dc";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://auth.rococo.dc";
    }
}
