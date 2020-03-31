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
        if (!isDoorClosed(event))
            return;

        smartHome.execute(obj -> {
            if (!(obj instanceof Room))
                return;
            Room room = (Room) obj;

            if (!(room.getName().equals("hall")))
                return;

            room.execute(inner_obj -> {
                if (!(inner_obj instanceof Door))
                    return;
                Door door = (Door) inner_obj;

                if (!(door.getId().equals(event.getObjectId())))
                    return;

                turnOffAllLights();
            });
        });
    }

    private boolean isDoorClosed(SensorEvent event) {
        return event.getType() == SensorEventType.DOOR_CLOSED;
    }

    private void turnOffAllLights() {
        smartHome.execute(obj -> {
            if (!(obj instanceof Room))
                return;
            Room room = (Room) obj;

            room.execute(inner_obj -> {
                if (!(inner_obj instanceof Light))
                    return;
                Light light = (Light) inner_obj;
                turnOffLight(light);
                logLightState(room, light);
            });
        });
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
