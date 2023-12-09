package guru.qa.config;

public interface Config {

    static Config getInstance() {
        if ("docker".equals(System.getProperty("test.env"))) {
            return DockerConfig.config;
        }
        return LocalConfig.config;
    }

    String databaseHost();
    String rococoFrontUrl();
    String rococoAuthUrl();

    default String databaseUser(){
        return "postgres";
    }

    default String databasePassword(){
        return "secret";
    }

    default int databasePort() {
        return 5432;
    }
}
