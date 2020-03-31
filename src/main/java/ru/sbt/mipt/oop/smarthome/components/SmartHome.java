package ru.sbt.mipt.oop.smarthome.components;

import ru.sbt.mipt.oop.smarthome.action.Action;
import ru.sbt.mipt.oop.smarthome.action.Actionable;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable {
    private Collection<Room> rooms;
    private transient Alarm alarm;

    public SmartHome(Alarm alarm) {
        rooms = new ArrayList<>();
        this.alarm = alarm;
    }

    public SmartHome(Collection<Room> rooms, Alarm alarm) {
        this.rooms = rooms;
        this.alarm = alarm;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
        alarm.execute(action);
        for (Actionable room : rooms) {
            room.execute(action);
        }
    }
}
