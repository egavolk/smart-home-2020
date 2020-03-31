package ru.sbt.mipt.oop.smarthome.events.handlers;

import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.events.SensorEvent;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.services.logger.DummyLogger;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DoorEventHandlerTest {
    private SmartHome smartHome;
    private List<Door> doors;
    private Logger logger = new DummyLogger();

    @Before
    public void setUp() {
        doors = Arrays.asList(
                new Door(false, "0"),
                new Door(true, "1")
        );
        Room myRoom = new Room(new ArrayList<>(), doors, "myRoom");
        smartHome = new SmartHome(Arrays.asList(myRoom), new Alarm());
    }

    @Test
    public void testOpenCLosedDoor() {
        SensorEvent event = new SensorEvent(EventType.DOOR_OPEN, "0");
        new DoorEventHandler(smartHome, logger).handle(event);
        assertTrue(doors.get(0).isOpen());
        assertTrue(doors.get(1).isOpen());
    }

    @Test
    public void testCloseClosedDoor() {
        SensorEvent event = new SensorEvent(EventType.DOOR_CLOSED, "0");
        new DoorEventHandler(smartHome, logger).handle(event);
        assertFalse(doors.get(0).isOpen());
        assertTrue(doors.get(1).isOpen());
    }

    @Test
    public void testOpenOpenedDoor() {
        SensorEvent event = new SensorEvent(EventType.DOOR_OPEN, "1");
        new DoorEventHandler(smartHome, logger).handle(event);
        assertFalse(doors.get(0).isOpen());
        assertTrue(doors.get(1).isOpen());
    }

    @Test
    public void testCloseOpenedDoor() {
        SensorEvent event = new SensorEvent(EventType.DOOR_CLOSED, "1");
        new DoorEventHandler(smartHome, logger).handle(event);
        assertFalse(doors.get(0).isOpen());
        assertFalse(doors.get(1).isOpen());
    }
}