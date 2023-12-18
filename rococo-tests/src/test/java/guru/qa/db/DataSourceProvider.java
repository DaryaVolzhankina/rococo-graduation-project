package guru.qa.db;

import guru.qa.config.Config;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DataSourceProvider {
    INSTANCE;

    private static final Config config = Config.getInstance();

    private final Map<ServiceDb, DataSource> dataSourcesStore = new ConcurrentHashMap<>();

    public DataSource getDataSource(ServiceDb db) {
        return dataSourcesStore.computeIfAbsent(db, key -> {
            PGSimpleDataSource sd = new PGSimpleDataSource();
            sd.setURL(key.getUrl());
            sd.setUser(config.databaseUser());
            sd.setPassword(config.databasePassword());
            return sd;
        });
    }
}
