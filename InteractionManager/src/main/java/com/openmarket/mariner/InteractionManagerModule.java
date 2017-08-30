package com.openmarket.mariner;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.openmarket.mariner.resource.AuthConstants;
import com.openmarket.mariner.tapons.TaponReceiver;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class InteractionManagerModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public WebTarget sqsWebTarget() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(AuthConstants.IDS_USERNAME, AuthConstants.IDS_PASSWORD);
        return ClientBuilder.newClient()
                            .target("http://sqs.openmarket.com/sqs/v1")
                            .register(feature);
    }


    @Provides
    @Singleton
    public TaponReceiver taponReceiver(WebTarget webTarget) {
        return new TaponReceiver(webTarget);
    }
}
