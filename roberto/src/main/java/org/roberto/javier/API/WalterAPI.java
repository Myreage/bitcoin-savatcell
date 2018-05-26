package org.roberto.javier.API;

import org.roberto.javier.PlugDB.PlugDB;
import org.roberto.javier.PlugDB.types.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.*;

@Api(value = "Walter API")
@Path("/walter")
public class WalterAPI {
    private HashSet<String> tokens = new HashSet<String>();
    private PlugDB pdb;

    public WalterAPI() throws Exception {
        // pdb = new PlugDB("ttyACM0");
    }

    private boolean isAuthenticated(String token) throws Exception {
        if(tokens.contains(token)) {
            return true;
        }

        if(pdb.loginWalter(token)) {
            tokens.add(token);
            return true;
        }

        return false;
    }

    @Path("/transaction")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response postTransaction(@QueryParam("token") String token, Transaction t) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        pdb.addTransaction(t);

        return Response.ok().build();
    }

    @Path("/wallet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response updateWallet(@QueryParam("token") String token, Wallet w) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        pdb.updateWallet(w);

        return Response.ok().build();
    }
}
