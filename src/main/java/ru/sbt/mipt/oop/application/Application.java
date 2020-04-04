package ru.sbt.mipt.oop.application;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;

public class Application {
    public static void main(String... args) throws IOException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SmartHomeConfiguration.class);
        SensorEventsManager eventManager = context.getBean(SensorEventsManager.class);
        eventManager.start();
    }
}
