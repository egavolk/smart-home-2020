package ru.sbt.mipt.oop;

public class DummyCommandSender implements CommandSender {
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
