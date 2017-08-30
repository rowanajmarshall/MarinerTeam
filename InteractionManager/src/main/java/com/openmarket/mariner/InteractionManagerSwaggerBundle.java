package com.openmarket.mariner;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public final class InteractionManagerSwaggerBundle extends SwaggerBundle<Configuration> {
    @Override
    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(Configuration unused) {
        SwaggerBundleConfiguration swaggerConfig = new SwaggerBundleConfiguration();
        swaggerConfig.setTitle("Dropwizard Starter");
        swaggerConfig.setResourcePackage("com.openmarket.mariner.resource");
        swaggerConfig.setSchemes(new String[]{});
        swaggerConfig.setDescription("A dropwizard starter project");
        return swaggerConfig;
    }
}
