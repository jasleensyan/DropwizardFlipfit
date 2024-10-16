package com.flipkart.business;

import com.flipkart.bean.Slot;
import com.flipkart.dao.SlotDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementation of SlotServiceInterface for managing gym slots.
 */
public class SlotServiceImpl implements SlotServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(SlotServiceImpl.class);
    private static final SlotDAOImpl slotDAO = new SlotDAOImpl();

    /**
     * Adds multiple slots for a specified gym center.
     *
     * @param gymCentreId the ID of the gym center where slots are to be added
     * @param slotList    the list of Slot objects to be added
     */
    @Override
    public void addSlotsForGym(String gymCentreId, List<Slot> slotList) {
        logger.info("Adding all slots to gym: {}", gymCentreId);
        for (Slot slot : slotList) {
            slot.setCentreId(gymCentreId);
            slotDAO.addSlot(slot);
            logger.debug("Added slot: {} to gym: {}", slot, gymCentreId);
        }
    }
}
