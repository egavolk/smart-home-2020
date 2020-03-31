package ru.sbt.mipt.oop.smarthome.components.alarm;

import ru.sbt.mipt.oop.smarthome.action.*;

public class Alarm implements Actionable {
    private AlarmState state;

    public Alarm() {
        this.state = new AlarmDeactivatedState(this);
    }

    public Alarm(AlarmState state) {
        this.state = state;
    }

    public void activate(String code) {
        state.activate(code);
    }

    public void deactivate(String code) {
        state.deactivate(code);
    }

    public void alert() {
        state.alert();
    }

    public AlarmState getState() {
        return state;
    }

    public void setState(AlarmState state) {
        this.state = state;
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
    }
}
