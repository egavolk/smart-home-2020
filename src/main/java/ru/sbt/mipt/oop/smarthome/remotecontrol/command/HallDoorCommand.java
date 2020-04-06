package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;

public class HallDoorCommand implements Command {
    private SmartHome smartHome;
    boolean open;

    public HallDoorCommand(SmartHome smartHome, boolean open) {
        this.smartHome = smartHome;
        this.open = open;
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
                if (!(innerObj instanceof Door))
                    return;

                ((Door)innerObj).setOpen(open);
            });
        });
    }
}
