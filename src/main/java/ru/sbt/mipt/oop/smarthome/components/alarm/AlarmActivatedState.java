package ru.sbt.mipt.oop.smarthome.components.alarm;

public class AlarmActivatedState implements AlarmState {
    private Alarm alarm;
    private String code;

    public AlarmActivatedState(Alarm alarm, String code) {
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
        } else {
            alarm.setState(new AlarmAlertedState(alarm, this.code));
        }
    }

    @Override
    public void alert() {
        alarm.setState(new AlarmAlertedState(alarm, this.code));
    }
}
