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
import java.util.HashSet;
import java.util.UUID;
import java.util.List;

import java.util.Calendar;
import java.util.Date;

import io.swagger.annotations.*;

@Api(value = "User API")
@Path("/user")
public class UserAPI {
    private HashSet<String> tokens = new HashSet<String>();
    private PlugDB pdb;

    public UserAPI() throws Exception {
        pdb = new PlugDB("ttyACM0");
    }

    private boolean isAuthenticated(String token) throws Exception {
        if(tokens.contains(token)) {
            return true;
        }

        if(pdb.userLoginSession(token)) {
            tokens.add(token);
            return true;
        }

        return false;
    }

    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response postLogin(String login, String pass) throws Exception {
        int id = pdb.userLogin(login, pass);

        if(id < 0) {
            return Response.status(403).build();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        // On a ajoute un jour
        c.add(Calendar.DATE, 1);
        Date expires = c.getTime();

        String token = UUID.randomUUID().toString();

        pdb.userSessionInsert(id, expires, token);

        tokens.add(token);

        return Response.ok().entity("{\"token\":\" + token + \"}").build();
    }

    @Path("/transaction/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response getTransactions(@QueryParam("token") String token, @PathParam("address") String address) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        List<Transaction> tlist = pdb.getTransactions(address);

        return Response.ok().entity(tlist.toString()).build();
    }

    @Path("/address/{idWallet}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response getAddresses(@QueryParam("token") String token, @PathParam("idWallet") int idWallet) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        List<Address> alist = pdb.getAddresses(idWallet);

        return Response.ok().entity(alist.toString()).build();
    }

    @Path("/wallet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response getWallets(@QueryParam("token") String token) throws Exception {
        if(!isAuthenticated(token)) {
            return Response.status(403).build();
        }

        List<Wallet> wlist = pdb.getWallets();

        return Response.ok().entity(wlist.toString()).build();
    }
}
