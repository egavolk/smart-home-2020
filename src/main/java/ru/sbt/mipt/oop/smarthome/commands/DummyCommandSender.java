package ru.sbt.mipt.oop.smarthome.commands;

public class DummyCommandSender implements CommandSender {
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
