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

        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (!door.getId().equals(event.getObjectId()))
                    continue;

                door.setOpen(event.getType() == SensorEventType.DOOR_OPEN);
                logDoorState(room, door);
            }
        }
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
