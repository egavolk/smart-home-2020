package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;

public class AllLightCommand implements Command {
    private SmartHome smartHome;
    boolean turnOn;

    public AllLightCommand(SmartHome smartHome, boolean turnOn) {
        this.smartHome = smartHome;
        this.turnOn = turnOn;
    }

    @Override
    public void execute() {
        smartHome.execute(obj -> {
            if (!(obj instanceof Light))
                return;

            ((Light)obj).setOn(turnOn);
        });
    }
}
