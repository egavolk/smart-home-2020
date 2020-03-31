package ru.sbt.mipt.oop;

import java.util.List;

public class SensorEventManager {
    private SensorEventProvider sensorEventProvider;
    private List<SensorEventHandler> handlers;
    private Logger logger;

    public SensorEventManager(SensorEventProvider sensorEventProvider,
                              List<SensorEventHandler> handlers,
                              Logger logger) {
        this.sensorEventProvider = sensorEventProvider;
        this.handlers = handlers;
        this.logger = logger;
    }

    public void runCycle() {
        SensorEvent event = sensorEventProvider.getNextSensorEvent();

        while (event != null) {
            logger.log("Got event: " + event);
            for (SensorEventHandler handler : handlers) {
                handler.handle(event);
            }
            event = sensorEventProvider.getNextSensorEvent();
        }
    }
}