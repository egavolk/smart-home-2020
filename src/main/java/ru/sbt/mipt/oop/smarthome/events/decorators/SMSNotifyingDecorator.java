package ru.sbt.mipt.oop.smarthome.events.decorators;

import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.components.alarm.AlarmAlertedState;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.handlers.EventHandler;

public class SMSNotifyingDecorator implements EventHandler {
    private final EventHandler slave;
    private final Alarm alarm;

    public SMSNotifyingDecorator(EventHandler slave, Alarm alarm) {
        this.alarm = alarm;
        this.slave = slave;
    }

    @Override
    public void handle(Event event) {
        slave.handle(event);
        if (alarm.getState() instanceof AlarmAlertedState) {
            System.out.println("Sending sms");
        }
    }
}
