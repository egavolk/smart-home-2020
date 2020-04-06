package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;

public class AlarmActivateCommand implements Command {
    private Alarm alarm;
    private String code;

    public AlarmActivateCommand(Alarm alarm, String code) {
        this.alarm = alarm;
        this.code = code;
    }

    @Override
    public void execute() {
        alarm.activate(code);
    }
}
