package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;

public class HallLightCommand implements Command {
    private SmartHome smartHome;
    boolean turnOn;

    public HallLightCommand(SmartHome smartHome, boolean turnOn) {
        this.smartHome = smartHome;
        this.turnOn = turnOn;
    }

    @Override
    public void execute() {
        smartHome.execute(obj -> {
            if (!(obj instanceof Room))
                return;

            Room room = (Room) obj;
            if (!"hall".equals(room.getName()))
                return;

            room.execute(innerObj -> {
                if (!(innerObj instanceof Light))
                    return;

                ((Light)innerObj).setOn(turnOn);
            });
        });
    }
}
