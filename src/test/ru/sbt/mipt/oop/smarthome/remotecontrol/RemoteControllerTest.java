package ru.sbt.mipt.oop.smarthome.remotecontrol;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RemoteControllerTest {
    private int value = 0;
    private RemoteController rc = new RemoteController(Map.ofEntries(
            Map.entry("A", () -> {value = 1;}),
            Map.entry("1", () -> {value = 2;})
    ));

    @Test
    public void testButtonPressed() {
        rc.onButtonPressed("A", "rc_id");
        assertEquals(1, value);
        rc.onButtonPressed("1", "rc_id");
        assertEquals(2, value);
    }
}