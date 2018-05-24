package org.roberto.API.Walter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.*;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("walter/myresource")
@Api(value = "My Sample Resource")
@Produces(MediaType.APPLICATION_JSON)
public class WalterAPI {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    public String[] getIt() {
        return new String[]{"Got it!"};
    }
}
