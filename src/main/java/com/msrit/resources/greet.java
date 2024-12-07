package com.msrit.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/greet")
public class greet {

    @GET
    public Response greet() {
        return Response.ok("Hello World!").build();
    }

    @GET
    @Path("/{name}")
    public Response greetByName(@PathParam("name") String name) {
        return Response.ok("Hello " + name).build();
    }

}
