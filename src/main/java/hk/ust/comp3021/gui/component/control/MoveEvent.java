package hk.ust.comp3021.gui.component.control;

import javafx.event.Event;
import javafx.event.EventType;

/**Move event*/
public class MoveEvent extends Event {
    /**
     * The event type of moving up.
     */
    public static final EventType<MoveEvent> UP_EVENT_TYPE = new EventType<>("UP");
    /**
     * The event type of moving down.
     */
    public static final EventType<MoveEvent> DOWN_EVENT_TYPE = new EventType<>("DOWN");
    /**
     * The event type of moving right.
     */
    public static final EventType<MoveEvent> RIGHT_EVENT_TYPE = new EventType<>("RIGHT");
    /**
     * The event type of moving left.
     */
    public static final EventType<MoveEvent> LEFT_EVENT_TYPE = new EventType<>("LEFT");

    private final int playerId;

    /**
     * @param type  The type of the event.
     * @param playerId The playId.
     */
    public MoveEvent(EventType<? extends MoveEvent> type, int playerId) {
        super(type);
        this.playerId = playerId;
    }

    /**
     * @return The playerId related to the event.
     */
    public int getPlayerId() {
        return playerId;
    }
}
