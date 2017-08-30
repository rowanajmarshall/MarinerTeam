package com.openmarket.mariner.resource;

import static com.openmarket.mariner.assertions.ResponseAssertions.assertThat;

import com.openmarket.mariner.model.ModelObject;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.ResourceTestRule;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class TestModelObjectResource {

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
                                                              .addResource(new SmsResource())
                                                              .build();

    private Targets targets;

    @BeforeClass
    public static void jerseyGuiceInit() {
        JerseyGuiceUtils.reset();
    }

    @Before
    public void setUp() {
        targets = new Targets(resources.client().target("/sms"));
    }

    @Test
    public void canReceiveSms() {
        final ModelObject modelObject = ModelObject.of("test");
        assertThat(targets.sms()
                          .request()
                          .get())
                .hasStatus(Response.Status.OK);
    }

    private static final class Targets {
        private final WebTarget baseTarget;

        Targets(WebTarget baseTarget) {
            this.baseTarget = baseTarget;
        }

        WebTarget sms() {
            return baseTarget;
        }
    }
}