package ru.sbt.mipt.oop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String... args) throws IOException {
        SmartHome smartHome = new SmartHomeJsonReader("smart-home-1.js").read();
        Logger logger = new ConsoleLogger();
        CommandSender sender = new DummyCommandSender();

        List<SensorEventHandler> handlers = Arrays.asList(
                new DoorSensorEventHandler(smartHome, logger),
                new LightSensorEventHandler(smartHome, logger),
                new HallDoorClosedEventHandler(smartHome, sender, logger)
        );

        SensorEventProvider provider = new RandomSensorEventProvider();
        SensorEventManager manager = new SensorEventManager(provider, handlers, logger);
        manager.runCycle();
    }
}
