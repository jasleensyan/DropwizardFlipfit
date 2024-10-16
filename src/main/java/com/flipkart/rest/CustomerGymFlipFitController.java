package com.flipkart.rest;


import com.flipkart.bean.BookingDetails;
import com.flipkart.bean.Customer;
import com.flipkart.bean.GymCentre;
import com.flipkart.business.CustomerServiceInterface;
import com.flipkart.exceptions.RegistrationFailedException;
import com.flipkart.exceptions.WrongCredentialsException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerGymFlipFitController {

    private final CustomerServiceInterface customerService;

    public CustomerGymFlipFitController(CustomerServiceInterface customerService) {
        this.customerService = customerService;
    }

    /**
     * Logs in the customer with the given userName and password.
     *
     * @param userName the username of the customer
     * @param password the password of the customer
     * @return HTTP Response indicating success or failure
     * @throws WrongCredentialsException if login credentials are incorrect
     */
    @POST
    @Path("/login")
    public Response customerLogin(@QueryParam("userName") String userName,
                                  @QueryParam("password") String password) {
        try {
            if (customerService.customerLogin(userName, password)) {
                String customerId = customerService.getCustomerIdFromNameAndPass(userName, password);
                return Response.ok("Login Successful. Customer ID: " + customerId).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Credentials").build();
            }
        } catch (WrongCredentialsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    /**
     * Registers a new customer.
     *
     * @param customer the customer object
     * @return HTTP Response indicating registration success or failure
     */
    @POST
    @Path("/register")
    public Response register(Customer customer) {
        try {
            Customer registeredCustomer = customerService.register(customer);
            String customerId = customerService.getCustomerIdFromNameAndPass(registeredCustomer.getUserName(),
                    registeredCustomer.getPassword());
            return Response.ok("Registration successful! Customer ID: " + customerId).build();
        } catch (RegistrationFailedException | WrongCredentialsException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Book a gym slot.
     *
     * @param userName the username of the customer
     * @param gymCentreId the ID of the gym center
     * @param timestampStr the timestamp for the booking (formatted as "yyyy-MM-dd HH:mm:ss")
     * @return HTTP Response indicating booking success or failure
     */
    @POST
    @Path("/book-slot")
    public Response bookSlot(@QueryParam("userName") String userName,
                             @QueryParam("gymCentreId") String gymCentreId,
                             @QueryParam("timestamp") String timestampStr) {
        try {
            Timestamp timestamp = parseTimestamp(timestampStr);
            String slotId = customerService.getSlotIdFromGymCentreAndTimestamp(gymCentreId, timestamp);
            if (slotId == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No slots available").build();
            }

            Integer currentBookingCount = customerService.getBookingCountFromSlotId(slotId);
            Integer maxBookingCapacity = customerService.getGymCentreCapacityFromCentreId(gymCentreId);

            if (currentBookingCount < maxBookingCapacity) {
                float gymCentreCost = customerService.getGymCentreCostFromCentreId(gymCentreId);
                String scheduleId = customerService.addSchedule(timestamp, slotId);
                String bookingId = customerService.addBooking(userName, scheduleId);
                String paymentId = customerService.addPayment(bookingId, gymCentreCost);
                return Response.ok("Booking successful! Booking ID: " + bookingId + ", Payment ID: " + paymentId).build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("No slots available").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error during booking: " + e.getMessage()).build();
        }
    }

    /**
     * View customer profile.
     *
     * @param customerId the customer ID
     * @return Customer profile
     */
    @GET
    @Path("/profile/{customerId}")
    public Response viewProfile(@PathParam("customerId") String customerId) {
        Customer customer = customerService.viewMyProfile(customerId);
        if (customer != null) {
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
        }
    }

    /**
     * Cancel a booking.
     *
     * @param bookingId the booking ID
     * @return HTTP Response indicating cancellation success or failure
     */
    @DELETE
    @Path("/cancel-booking")
    public Response cancelBooking(@QueryParam("bookingId") String bookingId) {
        try {
            customerService.cancelBooking(bookingId);
            return Response.ok("Booking canceled successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error canceling booking: " + e.getMessage()).build();
        }
    }

    /**
     * Parses a timestamp string to a SQL Timestamp.
     *
     * @param timestampStr the timestamp string
     * @return SQL Timestamp
     * @throws ParseException if the format is invalid
     */
    private Timestamp parseTimestamp(String timestampStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        return new Timestamp(dateFormat.parse(timestampStr).getTime());
    }
}
