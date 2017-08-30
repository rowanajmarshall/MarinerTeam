package com.openmarket.mariner;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.openmarket.mariner.journeys.JourneyService;
import com.openmarket.mariner.resource.AuthConstants;
import com.openmarket.mariner.sms.SmsSender;
import com.openmarket.mariner.tapons.SqsReceiver;
import com.openmarket.mariner.tapons.Tapoff;
import com.openmarket.mariner.tapons.Tapon;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class InteractionManagerModule extends AbstractModule {
    private static final HttpAuthenticationFeature AUTHENTICATION_FEATURE = HttpAuthenticationFeature.basic(AuthConstants.IDS_USERNAME, AuthConstants.IDS_PASSWORD);

    @Override
    protected void configure() {
        bind(WebTarget.class).annotatedWith(Names.named("sqs")).toProvider(() -> {

            return ClientBuilder.newClient()
                                .target("http://sqs.openmarket.com/sqs/v1")
                                .register(AUTHENTICATION_FEATURE);
        });

        bind(WebTarget.class).annotatedWith(Names.named("journeyApi")).toProvider(() -> {
            return ClientBuilder.newClient()
                                .target("http://localhost:5000/")
                                .register(AUTHENTICATION_FEATURE);
        });

        bind(WebTarget.class).annotatedWith(Names.named("sms")).toProvider(() -> {
            return ClientBuilder.newClient()
                                .target("https://sms.openmarket.com/sms/v1/send")
                                .register(AUTHENTICATION_FEATURE);
        });
    }

    @Provides
    @Singleton
    public SqsReceiver<Tapon> taponReceiver(@Named("sqs") WebTarget webTarget) {
        return new SqsReceiver<Tapon>(webTarget, "lta-tapon-queue", Tapon.class);
    }

    @Provides
    @Singleton
    public SqsReceiver<Tapoff> tapoffReceiver(@Named("sqs") WebTarget webTarget) {
        return new SqsReceiver<Tapoff>(webTarget, "lta-tapoff-queue", Tapoff.class);
    }

    @Provides
    @Singleton
    public JourneyService journeyService(@Named("journeyApi") WebTarget webTarget) {
        return new JourneyService(webTarget);
    }

    @Provides
    @Singleton
    public SmsSender smsSender(@Named("sms") WebTarget webTarget) {
        return new SmsSender(webTarget);
    }
}
