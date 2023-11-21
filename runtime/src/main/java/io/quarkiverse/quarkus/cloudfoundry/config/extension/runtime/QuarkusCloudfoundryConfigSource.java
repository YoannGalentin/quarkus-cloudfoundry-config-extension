package io.quarkiverse.quarkus.cloudfoundry.config.extension.runtime;

import java.util.Map;

import io.smallrye.config.common.MapBackedConfigSource;

public class QuarkusCloudfoundryConfigSource extends MapBackedConfigSource {

    public QuarkusCloudfoundryConfigSource(String name, Map<String, String> propertyMap, int defaultOrdinal) {
        super(name, propertyMap, defaultOrdinal);
    }
}
