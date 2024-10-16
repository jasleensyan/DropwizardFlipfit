package com.flipkart.rest;

import com.codahale.metrics.annotation.Timed;
import com.flipkart.business.AdminServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminGymFlipFitController {

    private final AdminServiceImpl adminService;

    public AdminGymFlipFitController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    /**
     * Admin login endpoint.
     *
     * @param userName the admin username
     * @param password the admin password
     * @return response with login status
     */
    @POST
    @Timed
    @Path("/login")
    public Response adminLogin(@QueryParam("userName") String userName,
                               @QueryParam("password") String password) {
        if (adminService.adminLogin(userName, password)) {
            return Response.ok("Login successful!").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed!").build();
        }
    }

    /**
     * Admin password change endpoint.
     *
     * @param userName    admin username
     * @param oldPassword old password
     * @param newPassword new password
     * @return response status of password change
     */
    @POST
    @Timed
    @Path("/change-password")
    public Response adminChangePassword(@QueryParam("userName") String userName,
                                        @QueryParam("oldPassword") String oldPassword,
                                        @QueryParam("newPassword") String newPassword) {
        adminService.adminChangePassword(userName, oldPassword, newPassword);
        return Response.ok("Password changed successfully!").build();
    }

    /**
     * View all gym owners.
     *
     * @return list of all gym owners
     */
    @GET
    @Timed
    @Path("/gym-owners")
    public Response viewAllGymOwners() {
        return Response.ok(adminService.viewAllGymOwners()).build();
    }

    /**
     * Approve all gym owners' requests.
     *
     * @return approval message
     */
    @POST
    @Timed
    @Path("/approve-all-gym-owners")
    public Response approveAllGymOwners() {
        adminService.validateAllGymOwners();
        return Response.ok("All gym owners' requests approved!").build();
    }

    /**
     * Approve a gym owner by ID.
     *
     * @param gymOwnerId gym owner ID
     * @return approval status
     */
    @POST
    @Timed
    @Path("/approve-gym-owner/{id}")
    public Response approveGymOwnerById(@PathParam("id") String gymOwnerId) {
        adminService.validateGymOwnerByID(gymOwnerId, 1);
        return Response.ok("Gym owner with ID " + gymOwnerId + " approved!").build();
    }

    /**
     * View all gym centres.
     *
     * @return list of all gym centres
     */
    @GET
    @Timed
    @Path("/gym-centres")
    public Response viewAllGymCentres() {
        return Response.ok(adminService.viewAllGymCentres()).build();
    }

    /**
     * Approve all gym centre requests.
     *
     * @return approval message
     */
    @POST
    @Timed
    @Path("/approve-all-gym-centres")
    public Response approveAllGymCentres() {
        adminService.validateAllGymCentres();
        return Response.ok("All gym centres' requests approved!").build();
    }

    /**
     * Approve a gym centre by ID.
     *
     * @param gymCentreId gym centre ID
     * @return approval status
     */
    @POST
    @Timed
    @Path("/approve-gym-centre/{id}")
    public Response approveGymCentreById(@PathParam("id") String gymCentreId) {
        adminService.validateGymCentreByID(gymCentreId, 1);
        return Response.ok("Gym centre with ID " + gymCentreId + " approved!").build();
    }
}
