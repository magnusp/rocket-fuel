package se.fortnox.rocketfuel;

import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.LockServiceFactory;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Migrator {
    private List<Liquibase> liquibaseList;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String jdbcUrl;
    String username;
    String password;

    public void setup() throws LiquibaseException, IOException {
        JdbcConnection conn = new JdbcConnection(getConnection(jdbcUrl, username, password));

        Enumeration<URL> resources = this.getClass()
            .getClassLoader()
            .getResources("migrations-spring.xml");
        if (!resources.hasMoreElements()) {
            throw new RuntimeException("Could not find migrations file migrations.xml");
        }

        liquibaseList = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL       url            = resources.nextElement();
            String    file           = url.toExternalForm();
            int       jarFileSep     = file.lastIndexOf('!');
            String    loggedFileName = file.substring(jarFileSep + 1);
            Liquibase liquibase      = new Liquibase(loggedFileName, new UrlAwareClassLoaderResourceAccessor(file), conn);
            liquibase.getDatabase().setDefaultSchemaName("public");

            liquibaseList.add(liquibase);
        }
    }

    private Database createDatabaseConnectionFromConfiguration(String jdbcUrl, String username, String password) {
        try {
            return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(getConnection(jdbcUrl, username, password)));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection(String jdbcUrl, String username, String password) {
        try {
            //DbDriver.loadDriver(jdbcUrl);
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() throws LiquibaseException {
        for (Liquibase liquibase : liquibaseList) {
            liquibase.update((String)null);
        }
    }

    public void drop() throws DatabaseException {
        for (Liquibase liquibase : liquibaseList) {
            liquibase.dropAll();
            break;
        }
        LockServiceFactory.getInstance().resetAll();
    }

    public void forceDrop() throws DatabaseException {
        for (Liquibase liquibase : liquibaseList) {
            Database         database = liquibase.getDatabase();
            CatalogAndSchema schema   = new CatalogAndSchema(database.getDefaultCatalogName(), database.getDefaultSchemaName());
            try {
                liquibase.checkLiquibaseTables(false, null, new Contexts(), new LabelExpression());
                database.dropDatabaseObjects(schema);
            } catch (DatabaseException e) {
                throw e;
            } catch (Exception e) {
                throw new DatabaseException(e);
            } finally {
                LockServiceFactory.getInstance().getLockService(database).destroy();
                LockServiceFactory.getInstance().resetAll();
                ChangeLogHistoryServiceFactory.getInstance().resetAll();
                ExecutorService.getInstance().reset();
            }
            return;
        }
    }

    void exit() {
        System.exit(0);
    }

    class UrlAwareClassLoaderResourceAccessor extends ClassLoaderResourceAccessor {
        private String realFileName;

        public UrlAwareClassLoaderResourceAccessor(String realFileName) {
            this.realFileName = realFileName;
        }

        @Override
        public Set<InputStream> getResourcesAsStream(String path) throws IOException {
            if (realFileName.endsWith(path)) {
                path = realFileName;
            }

            if (path.startsWith("file:") || path.startsWith("jar:file:")) {
                URL         url              = new URL(path);
                InputStream resourceAsStream = url.openStream();

                return new HashSet<>(List.of(resourceAsStream));
            }

            return super.getResourcesAsStream(path);
        }
    }
}
