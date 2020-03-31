package ru.sbt.mipt.oop;

public class DoorSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public DoorSensorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handle(SensorEvent event) {
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
                door.setOpen(event.getType() == SensorEventType.DOOR_OPEN);
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

    boolean isDoorEvent(SensorEvent event) {
        return event.getType() == SensorEventType.DOOR_CLOSED ||
               event.getType() == SensorEventType.DOOR_OPEN;
    }
}
