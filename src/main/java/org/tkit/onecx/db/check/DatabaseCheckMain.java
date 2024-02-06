package org.tkit.onecx.db.check;

import java.sql.Connection;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class DatabaseCheckMain implements QuarkusApplication {

    private static final Logger log = LoggerFactory.getLogger(DatabaseCheckMain.class);

    @Inject
    AgroalDataSource dataSource;

    @Inject
    DatabaseCheckConfig config;

    @Override
    public int run(String... args) throws Exception {

        // enable or disable database check
        if (!config.enabled()) {
            log.info("Database check is disabled.");
            return 0;
        }

        // registered shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> log.info("Database check was stop by signal.")));

        // check database
        while (true) {
            try (Connection connection = dataSource.getConnection()) {
                log.info("Database connection has been established.");
                return 0;
            } catch (Exception ex) {
                log.error("Error creating database connection. Error: {}", ex.getMessage());

                Thread.sleep(config.sleep().toMillis());
            }
        }
    }
}
