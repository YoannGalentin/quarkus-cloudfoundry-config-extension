package io.quarkiverse.quarkus.cloudfoundry.config.extension.deployment;

import io.quarkiverse.quarkus.cloudfoundry.config.extension.runtime.QuarkusCloudfoundryConfigSourceFactoryBuilder;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.RunTimeConfigBuilderBuildItem;
import io.quarkus.deployment.builditem.StaticInitConfigBuilderBuildItem;

class CloudfoundryConfigExtensionProcessor {

    private static final String FEATURE = "cloudfoundry-config-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    //    @BuildStep
    //    void consulConfigFactory(BuildProducer<RunTimeConfigBuilderBuildItem> runTimeConfigBuilder) {
    //        runTimeConfigBuilder
    //                .produce(new RunTimeConfigBuilderBuildItem(QuarkusCloudfoundryConfigSourceFactoryBuilder.class.getName()));
    //    }

    @BuildStep
    void cloudFoundryConfigFactory(
            BuildProducer<StaticInitConfigBuilderBuildItem> staticInitConfigBuilder,
            BuildProducer<RunTimeConfigBuilderBuildItem> runTimeConfigBuilder) {
        staticInitConfigBuilder
                .produce(new StaticInitConfigBuilderBuildItem(QuarkusCloudfoundryConfigSourceFactoryBuilder.class.getName()));
        runTimeConfigBuilder
                .produce(new RunTimeConfigBuilderBuildItem(QuarkusCloudfoundryConfigSourceFactoryBuilder.class.getName()));
    }
}
