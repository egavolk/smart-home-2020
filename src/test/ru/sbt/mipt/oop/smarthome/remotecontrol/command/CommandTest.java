package ru.sbt.mipt.oop.smarthome.remotecontrol.command;

import org.junit.Test;
import org.junit.Before;
import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.components.alarm.AlarmActivatedState;
import ru.sbt.mipt.oop.smarthome.components.alarm.AlarmAlertedState;
import ru.sbt.mipt.oop.smarthome.services.logger.DummyLogger;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CommandTest {
    private Alarm alarm = new Alarm();
    private SmartHome smartHome;
    private List<Light> allLights;
    private List<Door> hallDoors;
    private List<Light> hallLights;
    private Logger logger = new DummyLogger();

    @Before
    public void setUp() throws Exception {
        hallLights = Arrays.asList(
                new Light("light00", false),
                new Light("light01", true)
        );
        List<Light> myRoomLights = Arrays.asList(
                new Light("light10", false),
                new Light("light11", true)
        );
        hallDoors = Arrays.asList(
                new Door(false, "door00"),
                new Door(true, "door01")
        );
        List<Door> myRoomDoors = Arrays.asList(
                new Door(false, "door10"),
                new Door(true, "door11")
        );
        Room hall = new Room(hallLights, hallDoors, "hall");
        Room myRoom = new Room(myRoomLights, myRoomDoors, "room");
        smartHome = new SmartHome(Arrays.asList(hall, myRoom), new Alarm());
        allLights = new ArrayList<>();
        allLights.addAll(hallLights);
        allLights.addAll(myRoomLights);
    }

    @Test
    public void testAlarmActivate() {
        Command command = new AlarmActivateCommand(alarm, "code");
        command.execute();
        assertTrue(alarm.getState() instanceof AlarmActivatedState);
    }

    @Test
    public void testAlarmAlert() {
        (new AlarmAlertCommand(alarm)).execute();
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }

    @Test
    public void testAllLightOn() {
        (new AllLightCommand(smartHome, true)).execute();
        for (Light light : allLights) {
            assertTrue(light.isOn());
        }
    }

    @Test
    public void testAllLightOff() {
        (new AllLightCommand(smartHome, false)).execute();
        for (Light light : allLights) {
            assertFalse(light.isOn());
        }
    }

    @Test
    public void HallDoorOpen() {
        (new HallDoorCommand(smartHome, true)).execute();
        for (Door door : hallDoors) {
            assertTrue(door.isOpen());
        }
    }

    @Test
    public void HallDoorClose() {
        (new HallDoorCommand(smartHome, false)).execute();
        for (Door door : hallDoors) {
            assertFalse(door.isOpen());
        }
    }

    @Test
    public void HallLightOn() {
        (new HallLightCommand(smartHome, true)).execute();
        for (Light light : hallLights) {
            assertTrue(light.isOn());
        }
    }

    @Test
    public void HallLightOff() {
        (new HallLightCommand(smartHome, true)).execute();
        for (Light light : hallLights) {
            assertTrue(light.isOn());
        }
    }
}