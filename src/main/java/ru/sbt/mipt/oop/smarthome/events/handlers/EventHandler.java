package ru.sbt.mipt.oop.smarthome.events.handlers;

import ru.sbt.mipt.oop.smarthome.events.Event;

public interface EventHandler {
    void handle(Event event);
}
