package com.flipkart.business;

import com.flipkart.bean.Slot;
import java.util.List;

/**
 * Interface for Slot Service.
 * This interface defines methods for managing gym slots.
 *
 * @author JEDI-09
 */
public interface SlotServiceInterface {

    /**
     * Adds multiple slots to the specified gym center.
     *
     * @param gymCentreId the ID of the gym center to which slots are being added
     * @param slotList    a list of Slot objects representing the slots to be added
     */
    void addSlotsForGym(String gymCentreId, List<Slot> slotList);
}
