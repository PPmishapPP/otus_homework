package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.cachehw.MyCache;
import ru.otus.jdbc.core.datasource.DriverManagerDataSource;
import ru.otus.jdbc.core.executor.DbExecutorImpl;
import ru.otus.jdbc.core.mapper.EntityClassMetaData;
import ru.otus.jdbc.core.mapper.EntityConverter;
import ru.otus.jdbc.core.mapper.EntitySQLMetaData;
import ru.otus.jdbc.core.mapper.impl.*;
import ru.otus.jdbc.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.jdbc.model.Client;
import ru.otus.jdbc.service.DBServiceClient;
import ru.otus.jdbc.service.impl.DbCacheServiceClient;
import ru.otus.jdbc.service.impl.DbServiceClientImpl;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.IntStream;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        DBServiceClient dbServiceClient = createClientService(transactionRunner, dbExecutor);
        IntStream.range(0, 1000).forEach(i -> dbServiceClient.saveClient(new Client("Client " + i)));

        long start = System.currentTimeMillis();
        test(dbServiceClient);
        long end = System.currentTimeMillis();
        log.info("Без кеша отработало за {} мс", end - start);

        DBServiceClient cacheService = new DbCacheServiceClient(dbServiceClient, new MyCache<>());
        start = System.currentTimeMillis();
        test(cacheService);
        end = System.currentTimeMillis();
        log.info("А с кешем отработало за {} мс", end - start);

    }

    private static void test(DBServiceClient dbServiceClient) {
        List<Client> all = dbServiceClient.findAll();
        for (Client client : all) {
            dbServiceClient.getClient(client.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found, id:" + client.getId()));
        }
    }

    private static DBServiceClient createClientService(TransactionRunnerJdbc transactionRunner, DbExecutorImpl dbExecutor) {
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        ParamsProviderImpl<Client> paramsProviderClient = new ParamsProviderImpl<>(entityClassMetaDataClient);
        EntityConverter<Client> entityConverterClient = new EntityConverterImpl<>(entityClassMetaDataClient);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(
                dbExecutor, entitySQLMetaDataClient, paramsProviderClient, entityConverterClient);
        return new DbServiceClientImpl(transactionRunner, dataTemplateClient);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
