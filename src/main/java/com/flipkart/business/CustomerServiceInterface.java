package com.flipkart.business;

import com.flipkart.bean.BookingDetails;
import com.flipkart.bean.Customer;
import com.flipkart.bean.GymCentre;
import com.flipkart.exceptions.WrongCredentialsException;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface for Customer Service, providing various functionalities for managing
 * customer operations such as registration, login, booking slots, and viewing profiles.
 */
public interface CustomerServiceInterface {

    /**
     * Retrieves a list of gym centers based on the provided city.
     *
     * @param city the city for which gym center details are requested
     * @return a list of GymCentre objects representing gym centers in the specified city
     */
    List<GymCentre> getAllGymCenterDetailsByCity(String city);

    /**
     * Retrieves a list of bookings for the specified customer.
     *
     * @param customerId the ID of the customer whose bookings are requested
     * @return a list of BookingDetails objects representing the customer's bookings
     */
    List<BookingDetails> getCustomerBookings(String customerId);

    /**
     * Book a slot for the specified customer.
     *
     * @param userID  the ID of the customer who is booking the slot
     * @param date    the date on which the slot is being booked
     * @param slotId  the ID of the slot being booked
     * @param centreId the ID of the gym center where the slot is being booked
     * @return true if the slot is successfully booked, false otherwise
     */
    boolean bookSlot(String userID, Date date, String slotId, String centreId);

    /**
     * Cancels a booking for the specified customer.
     *
     * @param bookingID the ID of the booking to be canceled
     */
    void cancelBooking(String bookingID);

    /**
     * Logs in the customer with the provided username and password.
     *
     * @param userName the username of the customer
     * @param password the password of the customer
     * @return true if login is successful, false otherwise
     */
    boolean customerLogin(String userName, String password);

    /**
     * Registers a new customer.
     *
     * @param customer the Customer object containing registration details
     * @return the newly registered Customer object
     */
    Customer register(Customer customer);

    /**
     * Returns the profile of a customer for the provided customer ID.
     *
     * @param customerId the ID of the customer
     * @return the Customer object representing the customer's profile
     */
    Customer viewMyProfile(String customerId);

    /**
     * Changes the password of the customer.
     *
     * @param userName    the username of the customer
     * @param oldPassword the current password of the customer
     * @param newPassword the new password to set
     * @throws WrongCredentialsException if the old password does not match
     */
    void customerChangePassword(String userName, String oldPassword, String newPassword) throws WrongCredentialsException;

    /**
     * Returns the slot ID for the specified gym center and timestamp.
     *
     * @param gymCentreId the ID of the gym center
     * @param timestamp   the timestamp for the desired slot
     * @return the ID of the slot, or null if no slot is available
     */
    String getSlotIdFromGymCentreAndTimestamp(String gymCentreId, Timestamp timestamp);

    /**
     * Retrieves the customer ID from the username and password.
     *
     * @param userName the username of the customer
     * @param password the password of the customer
     * @return the customer ID
     * @throws WrongCredentialsException if the credentials are incorrect
     */
    String getCustomerIdFromNameAndPass(String userName, String password) throws WrongCredentialsException;

    /**
     * Retrieves the count of bookings for a given slot ID.
     *
     * @param slotId the ID of the slot for which the booking count is requested
     * @return the count of bookings for the specified slot
     */
    Integer getBookingCountFromSlotId(String slotId);

    /**
     * Retrieves the capacity of a gym center based on the provided center ID.
     *
     * @param centerId the ID of the gym center for which the capacity is requested
     * @return the capacity of the specified gym center
     */
    Integer getGymCentreCapacityFromCentreId(String centerId);

    /**
     * Adds a new schedule to the system.
     *
     * @param timestamp the timestamp for the schedule
     * @param slotId    the ID of the slot for the schedule
     * @return the ID of the newly created schedule
     */
    String addSchedule(Timestamp timestamp, String slotId);

    /**
     * Retrieves the cost of a gym center based on the provided center ID.
     *
     * @param centerId the ID of the gym center for which the cost is requested
     * @return the cost of the specified gym center
     */
    float getGymCentreCostFromCentreId(String centerId);

    /**
     * Adds a new payment to the system for a booking.
     *
     * @param bookingID  the ID of the booking for which the payment is made
     * @param amountPaid the amount paid for the booking
     * @return the ID of the newly created payment
     */
    String addPayment(String bookingID, float amountPaid);

    String addBooking(String userName, String scheduleId);
}
