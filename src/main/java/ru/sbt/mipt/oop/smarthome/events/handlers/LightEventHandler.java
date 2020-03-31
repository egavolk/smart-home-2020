package ru.sbt.mipt.oop.smarthome.events.handlers;

import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public LightEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handle(Event e) {
        if (!(e instanceof SensorEvent))
            return;

        SensorEvent event = (SensorEvent) e;
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
                light.setOn(event.getType() == EventType.LIGHT_ON);
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

    boolean isLightEvent(Event event) {
        return event.getType() == EventType.LIGHT_ON ||
                event.getType() == EventType.LIGHT_OFF;
    }
}
