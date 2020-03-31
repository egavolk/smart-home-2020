package ru.sbt.mipt.oop.smarthome.events.handlers;

import ru.sbt.mipt.oop.smarthome.commands.CommandSender;
import ru.sbt.mipt.oop.smarthome.commands.CommandType;
import ru.sbt.mipt.oop.smarthome.commands.SensorCommand;
import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

public class HallDoorClosedEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;
    private final CommandSender commandSender;

    public HallDoorClosedEventHandler(SmartHome smartHome, CommandSender commandSender, Logger logger) {
        this.smartHome = smartHome;
        this.commandSender = commandSender;
        this.logger = logger;
    }

    @Override
    public void handle(Event e) {
        if (!(e instanceof SensorEvent))
            return;

        SensorEvent event = (SensorEvent) e;
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

    private boolean isDoorClosed(Event event) {
        return event.getType() == EventType.DOOR_CLOSED;
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
