package io.quarkiverse.quarkus.cloudfoundry.config.extension.runtime;

import io.quarkus.runtime.configuration.ConfigBuilder;
import io.smallrye.config.SmallRyeConfigBuilder;

public class QuarkusCloudfoundryConfigSourceFactoryBuilder implements ConfigBuilder {
    @Override
    public SmallRyeConfigBuilder configBuilder(final SmallRyeConfigBuilder builder) {
        return builder.withSources(new QuarkusCloudfoundryConfigSourceFactory());
    }
}
