package org.tkit.onecx.db.check;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class DatabaseCheckMainMockTest extends AbstractTest {

    @Test
    void testException() throws SQLException {

        var config = Mockito.mock(DatabaseCheckConfig.class);
        Mockito.when(config.enabled()).thenReturn(true);
        Mockito.when(config.sleep()).thenReturn(Duration.ofSeconds(1));

        var dataSource = Mockito.mock(AgroalDataSource.class);
        Mockito.when(dataSource.getConnection()).thenThrow(new SQLException("test"))
                .thenReturn(Mockito.mock(Connection.class));

        DatabaseCheckMain main = new DatabaseCheckMain();
        main.config = config;
        main.dataSource = dataSource;

        Assertions.assertDoesNotThrow(() -> main.run());
    }

    @Test
    void testDisableConfig() {

        var config = Mockito.mock(DatabaseCheckConfig.class);
        Mockito.when(config.enabled()).thenReturn(false);

        DatabaseCheckMain main = new DatabaseCheckMain();
        main.config = config;

        Assertions.assertDoesNotThrow(() -> main.run());
    }

}
