package ru.sbt.mipt.oop.smarthome.events;

import ru.sbt.mipt.oop.smarthome.events.handlers.EventHandler;
import ru.sbt.mipt.oop.smarthome.events.providers.EventProvider;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

import java.util.List;

public class EventManagerImpl implements EventManager {
    private EventProvider eventProvider;
    private List<EventHandler> handlers;
    private Logger logger;

    public EventManagerImpl(EventProvider eventProvider,
                            List<EventHandler> handlers,
                            Logger logger) {
        this.eventProvider = eventProvider;
        this.handlers = handlers;
        this.logger = logger;
    }

    @Override
    public void runCycle() {
        Event event = eventProvider.getNextEvent();

        while (event != null) {
            logger.log("Got event: " + event);
            for (EventHandler handler : handlers) {
                handler.handle(event);
            }
            event = eventProvider.getNextEvent();
        }
    }
}