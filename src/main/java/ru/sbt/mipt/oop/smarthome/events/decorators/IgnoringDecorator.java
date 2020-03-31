package ru.sbt.mipt.oop.smarthome.events.decorators;

import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.components.alarm.AlarmAlertedState;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.handlers.EventHandler;

public class IgnoringDecorator implements EventHandler {
    private final EventHandler slave;
    private final Alarm alarm;

    public IgnoringDecorator(EventHandler slave, Alarm alarm) {
        this.slave = slave;
        this.alarm = alarm;
    }

    @Override
    public void handle(Event event) {
        if (alarm.getState() instanceof AlarmAlertedState) {
            return;
        }
        slave.handle(event);
    }
}
