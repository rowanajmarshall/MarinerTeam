package com.openmarket.mariner.it;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;

import com.openmarket.mariner.InteractionManagerApplication;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class ApiTest {
    @ClassRule
    public static final DropwizardAppRule DROPWIZARD_RULE = new DropwizardAppRule<>(InteractionManagerApplication.class,
                                                                                    resourceFilePath("test-configuration.yaml"));

    private final LocalhostUris uris = new LocalhostUris(DROPWIZARD_RULE.getLocalPort());
    private final Client client = ClientBuilder.newClient();

    @BeforeClass
    public static void cleanUpGuice() {
        JerseyGuiceUtils.reset();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void canSuccessfullyPost() {
//        Response response = client.target(uris.sms())
//                                  .request()
//                                  .get();
//        assertThat(response).hasStatus(Response.Status.OK);
    }
}
