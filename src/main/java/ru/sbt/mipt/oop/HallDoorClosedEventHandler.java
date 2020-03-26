package ru.sbt.mipt.oop;

public class HallDoorClosedEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private final Logger logger;
    private final CommandSender commandSender;

    public HallDoorClosedEventHandler(SmartHome smartHome, CommandSender commandSender, Logger logger) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
        this.logger = logger;
    }

    @Override
    public void handle(SensorEvent event) {
        if (isHallDoorClosed(event)) {
            turnOffAllLights();
        }
    }

    private boolean isHallDoorClosed(SensorEvent event) {
        Room room = findRoomByDoorId(event.getObjectId());
        return room != null && room.getName().equals("hall") &&
               event.getType() == SensorEventType.DOOR_CLOSED;
    }

    private Room findRoomByDoorId(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id))
                    return room;
            }
        }
        return null;
    }

    private void turnOffAllLights() {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                turnOffLight(light);
                logLightState(room, light);
            }
        }
    }

    private void logLightState(Room room, Light light) {
        logger.log(new StringBuilder()
                .append("Light ").append(light.getId())
                .append(" in Room ").append(room.getName())
                .append(" is ").append(light.isOn() ? "on" : "off")
                .toString());
    }

    private void turnOffLight(Light light) {
        light.setOn(false);
        SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
        commandSender.sendCommand(command);
    }
}
