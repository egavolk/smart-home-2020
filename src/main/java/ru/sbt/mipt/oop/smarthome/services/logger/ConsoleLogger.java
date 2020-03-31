package ru.sbt.mipt.oop.smarthome.services.logger;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String text) {
        System.out.println(text);
    }
}
