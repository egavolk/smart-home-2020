package ru.sbt.mipt.oop.smarthome.events;

public class AlarmEvent implements Event {
    private final EventType type;
    private final String code;

    public AlarmEvent(EventType type, String code) {
        this.type = type;
        this.code = code;
    }

    public EventType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "AlarmEvent{" +
                "type=" + type +
                ", code='" + code + '\'' +
                '}';
    }
}
