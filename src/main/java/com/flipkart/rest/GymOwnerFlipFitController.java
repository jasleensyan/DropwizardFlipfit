package com.flipkart.rest;

import com.flipkart.bean.GymOwner;
import com.flipkart.business.GymCenterServiceImpl;
import com.flipkart.business.GymCenterServiceInterface;
import com.flipkart.business.GymOwnerServiceImpl;
import com.flipkart.business.GymOwnerServiceInterface;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.flipkart.bean.*;
import com.flipkart.business.*;

import java.util.List;

@Path("/gymOwner")
@Produces(MediaType.APPLICATION_JSON)
public class GymOwnerFlipFitController {

    GymOwnerServiceInterface gymOwnerService = new GymOwnerServiceImpl();
    GymCenterServiceInterface gymCenterService = new GymCenterServiceImpl();
    private SlotServiceImpl slotServiceImpl = new SlotServiceImpl();

    @GET
    @Path("/login")
    public String GymOwnerLogin(@QueryParam("userName") String userName, @QueryParam("password") String password) {
        boolean loggedIn = gymOwnerService.gymOwnerLogin(userName, password);
        if(loggedIn){
            return "Login successful! Credentials are matching";
        }
        else{
            return "Login failed! Invalid Credentials";
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response GymOwnerRegister(GymOwner gymOwner) {
        GymOwner registerdGymOwner =  gymOwnerService.register(gymOwner);
        if(registerdGymOwner==null)
            return Response.notModified().build();
        return Response.ok(registerdGymOwner).build();
    }

    @GET
    @Path("/changePassword")
    @Produces(MediaType.APPLICATION_JSON)
    public String changePassword(@QueryParam("userName") String userName, @QueryParam("oldPassword") String oldPassword, @QueryParam("newPassword") String newPassword) {
        gymOwnerService.gymOwnerChangePassword(userName, oldPassword, newPassword);
        return "Password updated successfully.";
    }

    @GET
    @Path("/get-approval-owner")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestGymOwnerApproval(@QueryParam("gymOwnerId") String gymOwnerId) {
        gymOwnerService.requestGymOwnerApproval(gymOwnerId);
        return Response.ok("Sent approval request to Admin").build();
    }

    @GET
    @Path("/get-approval-center")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestGymCentreApproval(@QueryParam("gymCentreName") String gymCentreName, @QueryParam("userName") String userName) {
        gymCenterService.requestGymCentreApproval( gymCentreName, userName);
        return Response.ok("Sent Gym Center approval request to Admin").build();
    }

    @Path("/gym-centres")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCentresByGymOwnerUsername(@QueryParam("gymOwnerUsername") String gymOwnerUsername) {
        List<GymCentre> centres = gymCenterService.getAllCentresByOwnerUsername(gymOwnerUsername);
        if (centres == null || centres.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No gym centres found for the given owner ID.")
                    .build();
        }
        return Response.ok(centres).build();
    }

    @POST
    @Path("/add-centre")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addGymCentre(GymCentre gymCentre) {

        String gymId = gymCentre.getCentreId();
        String ownerUsername = gymCentre.getOwnerId();
        String gymCentreName = gymCentre.getCentreName();
        String gstin = gymCentre.getGstNo();
        String city = gymCentre.getCity();
        int capacity = gymCentre.getCapacity();
        boolean isApproved = gymCentre.isApproved() != 0; // Convert int to boolean
        float amountPerSlot = gymCentre.getAmountPerSlot();

        gymCenterService.addCenter(gymId, ownerUsername, gymCentreName, gstin, city, capacity, isApproved, amountPerSlot);

        return "center added successfully";
    }

    @POST
    @Path("/addSlots")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSlotsToGym(List<Slot> slotList, @QueryParam("centreId") String centreId){
        try {
            System.out.println(centreId);
            System.out.println(slotList);
            slotServiceImpl.addSlotsForGym(centreId, slotList);
            return Response.ok("Slots added in the Gym centre").build();
        } catch (IllegalArgumentException exp){
            System.out.println("illegal arg");
            return Response.notModified().build();
        }
    }
}
