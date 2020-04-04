package ru.sbt.mipt.oop.smarthome.remotecontrol;

import ru.sbt.mipt.oop.smarthome.remotecontrol.command.Command;
import ru.sbt.mipt.oop.smarthome.remotecontrol.command.DummyCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RemoteController implements rc.RemoteControl {
    private Map<String, Command> commandMap;

    public RemoteController(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        commandMap.get(buttonCode).execute();
    }
}
