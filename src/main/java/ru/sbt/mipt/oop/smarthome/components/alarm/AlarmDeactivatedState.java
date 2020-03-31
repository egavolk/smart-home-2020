package ru.sbt.mipt.oop.smarthome.components.alarm;

public class AlarmDeactivatedState implements AlarmState {
    private Alarm alarm;

    public AlarmDeactivatedState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate(String code) {
        alarm.setState(new AlarmActivatedState(alarm, code));
    }

    @Override
    public void deactivate(String code) {
    }

    @Override
    public void alert() {
    }

}
