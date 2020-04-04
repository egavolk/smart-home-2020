package ru.sbt.mipt.oop.smarthome.events.ccadapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;

import java.util.Map;

public class EventHandlerToCCAdapter implements EventHandler {
    private ru.sbt.mipt.oop.smarthome.events.handlers.EventHandler handler;
    private Map<String, EventType> getTypeByCCString;

    public EventHandlerToCCAdapter(ru.sbt.mipt.oop.smarthome.events.handlers.EventHandler handler,
                                   Map<String, EventType> getTypeByCCString) {
        this.handler = handler;
        this.getTypeByCCString = getTypeByCCString;
    }

    @Override
    public void handleEvent(CCSensorEvent ccEvent) {
        SensorEvent event = new SensorEvent(getTypeByCCString.get(ccEvent.getEventType()), ccEvent.getObjectId());
        handler.handle(event);
    }
}