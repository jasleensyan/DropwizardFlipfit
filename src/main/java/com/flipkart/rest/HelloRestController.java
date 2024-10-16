package com.flipkart.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/helloapi")
@Produces(MediaType.APPLICATION_JSON)
public class HelloRestController {
    @GET
    public String getEmployees(){
        return "my first dropwizard service";
    }
}
