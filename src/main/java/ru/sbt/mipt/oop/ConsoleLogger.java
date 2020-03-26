package ru.sbt.mipt.oop;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String text) {
        System.out.println(text);
    }
}
