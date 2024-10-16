package com.flipkart.business;

import com.flipkart.bean.BookingDetails;
import com.flipkart.bean.Customer;
import com.flipkart.bean.GymCentre;
import com.flipkart.dao.*;
import com.flipkart.exceptions.RegistrationFailedException;
import com.flipkart.exceptions.WrongCredentialsException;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class CustomerServiceImpl implements CustomerServiceInterface {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final GymCenterDAO gymCenterDAO = new GymCenterDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();
    private final ScheduleDAO scheduleDAO = new ScheduleDAOImpl();
    private final BookingServiceInterface bookingService = new BookingServiceImpl();
    private final BookingDAOImpl bookingDAO = new BookingDAOImpl();
    private final PaymentDAOImpl paymentDAO = new PaymentDAOImpl();

    @Override
    public List<GymCentre> getAllGymCenterDetailsByCity(String city) {
        return gymCenterDAO.getGymCentreListByCity(city);
    }

    @Override
    public List<BookingDetails> getCustomerBookings(String username) {
        return bookingService.getBookingByCustomerId(username);
    }

    @Override
    public boolean bookSlot(String userID, Date date, String slotId, String centreId) {
        return false; // You can implement this functionality if needed
    }

    @Override
    public void cancelBooking(String bookingID) {
        bookingService.cancelBooking(bookingID);
    }

    @Override
    public boolean customerLogin(String userName, String password) {
        return customerDAO.checkCustomerDetails(userName, password);
    }

    @Override
    public Customer register(Customer customer) throws RegistrationFailedException {
        return customerDAO.registerCustomer(customer.getUserName(), customer.getPassword(),
                customer.getEmail(), customer.getCustomerPhone(), customer.getCardDetails());
    }

    @Override
    public String getCustomerIdFromNameAndPass(String userName, String password) throws WrongCredentialsException {
        return customerDAO.getCustomerIdFromNameAndPass(userName, password);
    }

    @Override
    public Integer getBookingCountFromSlotId(String slotId) {
        return scheduleDAO.getSlotsBookedCountFromSlotId(slotId);
    }

    @Override
    public Integer getGymCentreCapacityFromCentreId(String centerId) {
        return gymCenterDAO.getCapacityFromCenterId(centerId);
    }

    @Override
    public String addSchedule(Timestamp timestamp, String slotId) {
        return scheduleDAO.addSchedule(timestamp, slotId);
    }

    @Override
    public float getGymCentreCostFromCentreId(String centerId) {
        return gymCenterDAO.getCostFromCenterId(centerId);
    }

    @Override
    public String addBooking(String customerId, String scheduleId) {
        return bookingDAO.addBooking(customerId, scheduleId);
    }

    @Override
    public String addPayment(String bookingId, float amount) {
        return paymentDAO.addPayment(bookingId, amount);
    }

    @Override
    public Customer viewMyProfile(String customerId) {
        return customerDAO.viewCustomerProfile(customerId);
    }

    @Override
    public void customerChangePassword(String userName, String oldPassword, String newPassword) throws WrongCredentialsException {

    }

    @Override
    public String getSlotIdFromGymCentreAndTimestamp(String gymCentreId, Timestamp timestamp) {
        return "";
    }
}
