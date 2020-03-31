package ru.sbt.mipt.oop.smarthome.components.alarm;

public class AlarmAlertedState implements AlarmState {
    private Alarm alarm;
    private String code;

    public AlarmAlertedState(Alarm alarm, String code) {
        this.alarm = alarm;
        this.code = code;
    }


    @Override
    public void activate(String code) {

    }

    @Override
    public void deactivate(String code) {
        if (this.code.equals(code)) {
            alarm.setState(new AlarmDeactivatedState(alarm));
        }
    }

    @Override
    public void alert() {

    }
}
