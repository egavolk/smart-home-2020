package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;

public class AlarmAlertCommand implements Command {
    private Alarm alarm;

    public AlarmAlertCommand(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.alert();
    }
}
