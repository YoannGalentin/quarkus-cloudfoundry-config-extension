package io.quarkiverse.quarkus.cloudfoundry.config.extension.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pivotal.cfenv.core.CfService;
//import io.pivotal.cfenv.core.JsonIoConverter;
import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;

public class QuarkusCloudfoundryConfigSourceFactory
        implements ConfigSourceFactory.ConfigurableConfigSourceFactory<QuarkusCloudfoundryConfigConfig> {
    private static final Logger log = Logger.getLogger(QuarkusCloudfoundryConfigSourceFactory.class);
    private static final String PROPERTY_PREFIX = "cfenv.";

    @Override
    public Iterable<ConfigSource> getConfigSources(final ConfigSourceContext context,
            final QuarkusCloudfoundryConfigConfig config) {
        if (!config.enabled()) {
            return Collections.emptyList();
        }

        log.info("QuarkusCloudfoundryConfig extension is enabled");

        // get VCAP_SERVICES from Env variables
        String vcapServicesJson = System.getenv("VCAP_SERVICES");

        if (vcapServicesJson == null || vcapServicesJson.trim().isEmpty()) {
            log.warn("VCAP_SERVICES environment variable has not been set");
            return Collections.emptyList();
        }

        log.warn("VCAP_SERVICES environment variable has been defined");

        List<CfService> cfServices = new ArrayList<>();

        // TODO: wait JDK17 support for quarkus exntesion
        // Map<String, List<Map<String, Object>>> rawServicesMap = JsonIoConverter.jsonToJavaWithListsAndInts(vcapServicesJson);

        Map<String, List<Map<String, Object>>> rawServicesMap;

        try {
            rawServicesMap = (Map) new ObjectMapper().readValue(vcapServicesJson, Map.class);
        } catch (JsonProcessingException e) {
            log.error("error during json parsing of the env var 'VCAP_SERVICES'");
            return Collections.emptyList();
        }

        rawServicesMap.values().stream().flatMap(Collection::stream).forEach((serviceData) -> {
            cfServices.add(new CfService(serviceData));
        });

        Map<String, String> propertyMap = new HashMap<>();

        cfServices.forEach(cfService -> {
            String label = cfService.getLabel();
            String name = cfService.getName();

            Map<String, String> credentials = (Map<String, String>) cfService.getCredentials().getMap().get("credentials");

            if (credentials != null && !credentials.isEmpty()) {
                credentials.forEach((key, value) -> {
                    propertyMap.put(PROPERTY_PREFIX + label + "." + name + "." + key, value);
                });
            }
        });

        return Collections.singletonList(new QuarkusCloudfoundryConfigSource("cloudfoundry-config", propertyMap, 400));
    }
}
