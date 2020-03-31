package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HallDoorClosedEventHandlerTest {
    private SmartHome smartHome;
    private CommandSender commandSender = new DummyCommandSender();
    private List<Light> allLights;
    private Logger logger = new DummyLogger();

    @Before
    public void setUp() throws Exception {
        List<Light> hallLights = Arrays.asList(
                new Light("light00", false),
                new Light("light01", true)
        );
        List<Light> myRoomLights = Arrays.asList(
                new Light("light10", false),
                new Light("light11", true)
        );
        List<Door> hallDoors = Arrays.asList(
                new Door(false, "door00")
        );
        List<Door> myRoomDoors = Arrays.asList(
                new Door(false, "door10")
        );
        Room hall = new Room(hallLights, hallDoors, "hall");
        Room myRoom = new Room(myRoomLights, myRoomDoors, "room");
        smartHome = new SmartHome(Arrays.asList(hall, myRoom));
        allLights = new ArrayList<>();
        allLights.addAll(hallLights);
        allLights.addAll(myRoomLights);
    }

    @Test
    public void closeHallDoorTurnOffAllLight() {
        SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, "door00");
        new HallDoorClosedEventHandler(smartHome, commandSender, logger).handle(event);
        for (Light light : allLights) {
            assertFalse(light.isOn());
        }
    }

    @Test
    public void closeMyRoomDoorDontAffectLight() {
        SensorEvent event = new SensorEvent(SensorEventType.DOOR_CLOSED, "door10");
        new HallDoorClosedEventHandler(smartHome, commandSender, logger).handle(event);
        for (Light light : allLights) {
            if (light.getId().charAt(6) == '0') {
                assertFalse(light.isOn());
            } else {
                assertTrue(light.isOn());
            }
        }
    }
}