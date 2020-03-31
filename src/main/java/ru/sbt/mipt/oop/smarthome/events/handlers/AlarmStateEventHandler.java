package ru.sbt.mipt.oop.smarthome.events.handlers;

import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.events.AlarmEvent;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

public class AlarmStateEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public AlarmStateEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handle(Event e) {
        if (!(e instanceof AlarmEvent))
            return;

        AlarmEvent event = (AlarmEvent) e;

        smartHome.execute(obj -> {
            if (!(obj instanceof Alarm)) return;

            Alarm alarm = (Alarm) obj;
            if (event.getType() == EventType.ALARM_ACTIVATE) {
                alarm.activate(event.getCode());
                logger.log("Activate alarm with code " + event.getCode());
            } else {
                alarm.deactivate(event.getCode());
                logger.log("Dectivate alarm with code " + event.getCode());
            }
        });
    }

    boolean isAlarmEvent(Event event) {
        return event instanceof AlarmEvent;
    }
}
