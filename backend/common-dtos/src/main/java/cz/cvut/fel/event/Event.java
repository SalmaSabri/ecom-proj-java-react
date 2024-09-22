package cz.cvut.fel.event;

import java.util.Date;
import java.util.UUID;

/**
 * Interface representing a generic event in the system.
 *
 * All events must provide an event ID and a timestamp for when the event occurred.
 */
public interface Event {

    /**
     * Gets the unique identifier of the event.
     *
     * @return the event ID.
     */
    UUID getEventId();

    /**
     * Gets the date when the event occurred.
     *
     * @return the event date.
     */
    Date getDate();
}
