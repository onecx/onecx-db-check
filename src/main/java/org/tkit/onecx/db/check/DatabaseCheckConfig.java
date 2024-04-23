package org.tkit.onecx.db.check;

import java.time.Duration;

import io.quarkus.runtime.annotations.ConfigDocFilename;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

/**
 * Database check configuration.
 */
@ConfigDocFilename("onecx-db-check.adoc")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
@ConfigMapping(prefix = "onecx.db-check")
public interface DatabaseCheckConfig {

    /**
     * Enable or disable database check.
     */
    @WithName("enabled")
    @WithDefault("true")
    boolean enabled();

    /**
     * Sleep check period.
     */
    @WithName("sleep")
    @WithDefault("PT2S")
    Duration sleep();
}
