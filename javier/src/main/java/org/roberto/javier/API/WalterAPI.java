package org.roberto.javier.API;

import org.roberto.javier.PlugDB.PlugDB;
import org.roberto.javier.PlugDB.types.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.servlet.ServletContext;
import java.util.HashSet;

import io.swagger.annotations.*;

@Api(value = "Walter API")
@Path("/walter")
public class WalterAPI {
    private HashSet<String> tokens = new HashSet<String>();
    private PlugDB pdb;

    public WalterAPI(@Context ServletContext context) throws Exception {
        pdb = (PlugDB)context.getAttribute("pdb");
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

    @Path("/address")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response postAddress(@QueryParam("token") String token, Address a) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        pdb.addAddress(a);

        return Response.ok().build();
    }

    @Path("/wallet/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response updateWallet(@QueryParam("token") String token, @PathParam("id") int id, Wallet w) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        w.idGlobal = id;
        pdb.updateWallet(w);

        return Response.ok().build();
    }

    @Path("/initdb")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response initDB() throws Exception {
        pdb.initDB();

        return Response.ok().build();
    }
}
