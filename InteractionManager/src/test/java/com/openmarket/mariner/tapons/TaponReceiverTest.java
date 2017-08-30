package com.openmarket.mariner.tapons;

import com.openmarket.mariner.resource.AuthConstants;
import java.io.IOException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Ignore;
import org.junit.Test;

public class TaponReceiverTest {

    @Test
    @Ignore
    public void receiveTapon() throws IOException {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(AuthConstants.IDS_USERNAME, AuthConstants.IDS_PASSWORD);
        WebTarget target = ClientBuilder.newClient()
                            .target("https://sqs.openmarket.com/sqs/v1")
                            .register(feature);
    }
}