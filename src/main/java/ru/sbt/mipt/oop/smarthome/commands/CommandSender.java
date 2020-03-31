package ru.sbt.mipt.oop.smarthome.commands;

public interface CommandSender {
    void sendCommand(SensorCommand command);
}
