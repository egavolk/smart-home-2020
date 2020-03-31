package ru.sbt.mipt.oop.smarthome.events.handlers;

import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

public class DoorEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public DoorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handle(Event e) {
        if (!(e instanceof SensorEvent))
            return;

        SensorEvent event = (SensorEvent) e;
        if (!isDoorEvent(event))
            return;

        smartHome.execute(obj -> {
            if (!(obj instanceof Room))
                return;
            Room room = (Room) obj;

            room.execute(inner_obj -> {
                if (!(inner_obj instanceof Door))
                    return;
                Door door = (Door) inner_obj;

                if (!door.getId().equals(event.getObjectId()))
                    return;
                door.setOpen(event.getType() == EventType.DOOR_OPEN);
                logDoorState(room, door);
            });
        });
    }

    void logDoorState(Room room, Door door) {
        logger.log(new StringBuilder()
                        .append("Door ").append(door.getId())
                        .append(" in Room ").append(room.getName())
                        .append(" is ").append(door.isOpen() ? "open" : "close")
                        .toString());
    }

    boolean isDoorEvent(Event event) {
        return event.getType() == EventType.DOOR_CLOSED ||
               event.getType() == EventType.DOOR_OPEN;
    }
}
