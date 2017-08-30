package com.openmarket.mariner.resource;

import com.openmarket.mariner.model.ModelObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sms")
@Api(value = "SMS resource")
public class SmsResource {

    @Inject
    public SmsResource() {
    }

    @GET
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Receive SMS",
                  response = ModelObject.class)
    public Response get(@QueryParam("smsfrom") String phoneNumber, @QueryParam("smsmsg") String message) {
        System.out.println("Got SMS " +  message + " from " + phoneNumber);
        return Response.ok().build();
    }
}
