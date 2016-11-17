package cz.vsb.p4.testapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by trehak on 10.10.2016.
 */
@Path("/")
@Produces("application/json;charset=utf-8")
@Singleton
public class ZjsRESTResource {

    private final ZsjRepository zsjRepository;

    @Autowired
    public ZjsRESTResource(ZsjRepository zsjRepository) {
        this.zsjRepository = zsjRepository;
    }

    // TODO implement REST strom manipulace

    @GET
    @Path("/echo/{text}")
    public String echo(@PathParam("text") String text) {
        return "Třebovice";
    }


}
