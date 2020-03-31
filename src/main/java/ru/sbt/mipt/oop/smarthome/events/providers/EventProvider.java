package ru.sbt.mipt.oop.smarthome.events.providers;

import ru.sbt.mipt.oop.smarthome.events.Event;

public interface EventProvider {
    Event getNextEvent();
}


