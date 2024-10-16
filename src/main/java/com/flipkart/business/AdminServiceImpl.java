package com.flipkart.business;

import com.flipkart.bean.Admin;
import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;
import com.flipkart.dao.AdminDAOImpl;
import com.flipkart.exceptions.LoginFailedException;

import java.util.List;
import java.util.Objects;

public class AdminServiceImpl implements AdminServiceInterface {

    private final Admin admin = new Admin();
    private final AdminDAOImpl adminDAO = new AdminDAOImpl();

    // Validates the username and password for admin login
    @Override
    public boolean isUserValid(String userName, String password) {
        return userName.equals(admin.getUserName()) && password.equals(admin.getPassword());
    }

    // Admin login service method
    @Override
    public boolean adminLogin(String userName, String password) {
        if (isUserValid(userName, password)) {
            return true;  // Login successful
        } else {
            throw new LoginFailedException("Admin Login Failed");
        }
    }

    // Admin password change service method
    @Override
    public void adminChangePassword(String userName, String oldPassword, String newPassword) {
        if (Objects.equals(oldPassword, admin.getPassword())) {
            admin.setPassword(newPassword);
        } else {
            throw new IllegalArgumentException("Entered wrong password");
        }
    }

    // View all gym owners service method
    @Override
    public List<GymOwner> viewAllGymOwners() {
        return adminDAO.getGymOwnersList();
    }

    // View all gym centres service method
    @Override
    public List<GymCentre> viewAllGymCentres() {
        return adminDAO.getGymCentersList();
    }

    // Approve all gym centres service method
    @Override
    public void validateAllGymCentres() {
        adminDAO.validateAllGymCentres();
    }

    // Approve a specific gym centre by ID
    @Override
    public void validateGymCentreByID(String gymCentreId, int isApproved) {
        adminDAO.validateGymCentreByID(gymCentreId, isApproved);
    }

    // Approve all gym owners service method
    @Override
    public void validateAllGymOwners() {
        adminDAO.validateAllGymOwners();
    }

    // Approve a specific gym owner by ID
    @Override
    public void validateGymOwnerByID(String ownerId, int isApproved) {
        adminDAO.validateGymOwnerByID(ownerId, isApproved);
    }
}
