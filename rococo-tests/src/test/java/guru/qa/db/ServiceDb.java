package guru.qa.db;

import guru.qa.config.Config;

public enum ServiceDb {
    AUTH("jdbc:postgresql://%s:%d/rococo-auth"),
    USERDATA("jdbc:postgresql://%s:%d/rococo-userdata"),
    ARTIST("jdbc:postgresql://%s:%d/rococo-artist"),
    MUSEUM("jdbc:postgresql://%s:%d/rococo-museum"),
    PAINTING("jdbc:postgresql://%s:%d/rococo-painting");

    private final String url;
    private static final Config config = Config.getInstance();

    ServiceDb(String url) {
        this.url = url;
    }

    public String getUrl() {
        return String.format(url, config.databaseHost(), config.databasePort());
    }
}
