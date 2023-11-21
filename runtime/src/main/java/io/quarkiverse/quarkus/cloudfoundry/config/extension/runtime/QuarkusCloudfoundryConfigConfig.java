package io.quarkiverse.quarkus.cloudfoundry.config.extension.runtime;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "quarkus.config.cloudfoundry")
@ConfigRoot(phase = ConfigPhase.RUN_TIME)
public interface QuarkusCloudfoundryConfigConfig {
    /**
     * If set to true, the application will attempt to look up the configuration from env vars
     */
    @WithDefault("true")
    boolean enabled();
}
