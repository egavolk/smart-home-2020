package ru.sbt.mipt.oop;

public class LightSensorEventHandler implements SensorEventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public LightSensorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handle(SensorEvent event) {
        if (!isLightEvent(event))
            return;

        smartHome.execute(obj -> {
            if (!(obj instanceof Room))
                return;
            Room room = (Room) obj;

            room.execute(inner_obj -> {
                if (!(inner_obj instanceof Light))
                    return;
                Light light = (Light) inner_obj;

                if (!light.getId().equals(event.getObjectId()))
                    return;
                light.setOn(event.getType() == SensorEventType.LIGHT_ON);
                logLightState(room, light);
            });
        });
    }

    void logLightState(Room room, Light light) {
        logger.log(new StringBuilder()
                .append("Light ").append(light.getId())
                .append(" in Room ").append(room.getName())
                .append(" is ").append(light.isOn() ? "on" : "off")
                .toString());
    }

    boolean isLightEvent(SensorEvent event) {
        return event.getType() == SensorEventType.LIGHT_ON ||
                event.getType() == SensorEventType.LIGHT_OFF;
    }
}
