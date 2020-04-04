package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LightSensorEventHandlerTest {
    private SmartHome smartHome;
    private List<Light> lights;
    private Logger logger = new DummyLogger();

    @Before
    public void setUp() {
        lights = Arrays.asList(
                new Light("0", false),
                new Light("1", true)
        );
        Room myRoom = new Room(lights, new ArrayList<>(), "myRoom");
        smartHome = new SmartHome(Arrays.asList(myRoom));
    }

    @Test
    public void testTurnOnTurnedOffLight() {
        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON, "0");
        new LightSensorEventHandler(smartHome, logger).handle(event);
        assertTrue(lights.get(0).isOn());
        assertTrue(lights.get(1).isOn());
    }

    @Test
    public void testTurnOffTurnedOffLight() {
        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, "0");
        new LightSensorEventHandler(smartHome, logger).handle(event);
        assertFalse(lights.get(0).isOn());
        assertTrue(lights.get(1).isOn());
    }

    @Test
    public void testTurnOnTurnedOnLight() {
        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_ON, "1");
        new LightSensorEventHandler(smartHome, logger).handle(event);
        assertFalse(lights.get(0).isOn());
        assertTrue(lights.get(1).isOn());
    }

    @Test
    public void testTurnOffTurnedOnLight() {
        SensorEvent event = new SensorEvent(SensorEventType.LIGHT_OFF, "1");
        new LightSensorEventHandler(smartHome, logger).handle(event);
        assertFalse(lights.get(0).isOn());
        assertFalse(lights.get(1).isOn());
    }
}