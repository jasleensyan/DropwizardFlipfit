package com.flipkart.business;

import com.flipkart.bean.GymCentre;
import com.flipkart.bean.GymOwner;

import java.util.List;

/**
 * @author JEDI-09
 * Interface for Admin Service in the context of a REST API
 */
public interface AdminServiceInterface {

    /**
     * Checks if the given userName and password combination is valid.
     *
     * @param  userName   the userName to be checked
     * @param  password   the password to be checked
     * @return            true if the userName and password combination is valid, false otherwise
     */
    public boolean isUserValid(String userName, String password);

    /**
     * Log in for Admin.
     *
     * @param  userName   the userName to be checked
     * @param  password   the password to be checked
     * @return            true if the userName and password combination is valid, false otherwise
     */
    public boolean adminLogin(String userName, String password);

    /**
     * Changes the password of the admin.
     *
     * @param  userName      the userName of the admin
     * @param  oldPassword   the old password of the admin
     * @param  newPassword   the new password of the admin
     */
    public void adminChangePassword(String userName, String oldPassword, String newPassword);

    /**
     * Retrieves a list of all Gym Owners.
     *
     * @return  a list of Gym Owners
     */
    public List<GymOwner> viewAllGymOwners();

    /**
     * Validates all Gym Owners.
     *
     * This method validates each Gym Owner by updating their approval status.
     */
    public void validateAllGymOwners();

    /**
     * Validates a Gym Owner by ID.
     *
     * @param  ownerId    the ID of the Gym Owner to validate
     * @param  isApproved the approval status of the Gym Owner (1 for approved, 0 for not approved)
     */
    public void validateGymOwnerByID(String ownerId, int isApproved);

    /**
     * Retrieves a list of all Gym Centres.
     *
     * @return  a list of Gym Centres
     */
    public List<GymCentre> viewAllGymCentres();

    /**
     * Validates all Gym Centres.
     *
     * This method validates each Gym Centre by updating their approval status.
     */
    public void validateAllGymCentres();

    /**
     * Validates a Gym Centre by its ID.
     *
     * @param  gymCentreId   the ID of the Gym Centre to validate
     * @param  isApproved    the approval status of the Gym Centre (1 for approved, 0 for not approved)
     */
    public void validateGymCentreByID(String gymCentreId, int isApproved);
}
