package com.openmarket.mariner;

import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import de.spinscale.dropwizard.jobs.GuiceJobsBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class InteractionManagerApplication extends Application<InteractionManagerConfiguration> {
    private GuiceBundle<InteractionManagerConfiguration> guiceBundle;
    private Injector injector;

    public static void main(String[] args) throws Exception {
        new InteractionManagerApplication().run(args);
    }

    @Override
    public void run(InteractionManagerConfiguration dropwizardStarterConfiguration, Environment environment) throws Exception {
        environment.jersey().packages("com.openmarket.mariner.resource");

//        SqsReceiver<Tapon> taponReceiver = injector.getInstance(Key.get(new TypeLiteral<SqsReceiver<Tapon>>(){}));
//        System.out.println(taponReceiver.receive());
//
//        JourneyService journeyService = injector.getInstance(JourneyService.class);
//        System.out.println(journeyService.getJourney("Gunnersbury", "Kentish Town", true));

    }

    @Override
    public void initialize(Bootstrap<InteractionManagerConfiguration> bootstrap) {
        guiceBundle = GuiceBundle.<InteractionManagerConfiguration>newBuilder()
                                 .addModule(new InteractionManagerModule())
                                 .enableAutoConfig(getClass().getPackage().getName())
                                 .setConfigClass(InteractionManagerConfiguration.class)
                                 .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new GuiceJobsBundle(guiceBundle.getInjector()));
        bootstrap.addBundle(new InteractionManagerSwaggerBundle());
        this.injector = guiceBundle.getInjector();
    }

}
