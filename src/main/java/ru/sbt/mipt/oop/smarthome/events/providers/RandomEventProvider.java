package ru.sbt.mipt.oop.smarthome.events.providers;

import ru.sbt.mipt.oop.smarthome.events.AlarmEvent;
import ru.sbt.mipt.oop.smarthome.events.Event;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;

public class RandomEventProvider implements EventProvider {

    @Override
    public Event getNextEvent() {
        // pretend like we're getting the events from physical world, but here we're going to just generate some random events
        if (Math.random() < 0.05)
            return null; // null means end of event stream
        if (Math.random() < 2.0 / 3) {
            return getNextSensorEvent();
        } else {
            return getNextAlarmEvent();
        }
    }

    private SensorEvent getNextSensorEvent() {
        EventType eventType = EventType.values()[(int) (4 * Math.random())];
        String objectId = "" + ((int) (10 * Math.random()));
        return new SensorEvent(eventType, objectId);
    }

    private AlarmEvent getNextAlarmEvent() {
        EventType eventType = EventType.values()[4 + (int) (2 * Math.random())];
        String code = "" + ((int) (2 * Math.random()));
        return new AlarmEvent(eventType, code);
    }
}
