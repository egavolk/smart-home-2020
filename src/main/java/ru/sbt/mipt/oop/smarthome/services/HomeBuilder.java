package ru.sbt.mipt.oop.smarthome.services;

import ru.sbt.mipt.oop.smarthome.components.Door;
import ru.sbt.mipt.oop.smarthome.components.Light;
import ru.sbt.mipt.oop.smarthome.components.Room;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.services.writer.SmartHomeJsonWriter;
import ru.sbt.mipt.oop.smarthome.services.writer.SmartHomeWriter;

import java.io.IOException;
import java.util.Arrays;

public class HomeBuilder {

    public static void main(String[] args) throws IOException {
        runHomeBuilder(new SmartHomeJsonWriter("smart-home-1.json"));
    }

    private static void runHomeBuilder(SmartHomeWriter writer) {
        SmartHome smartHome = makeMyCustomSmartHome();
        writer.write(smartHome);
    }

    private static SmartHome makeMyCustomSmartHome() {
        Room kitchen = new Room(
                Arrays.asList(
                        new Light("1", false),
                        new Light("2", true)
                ),
                Arrays.asList(
                        new Door(false, "1")
                ),
                "kitchen"
        );
        Room bathroom = new Room(
                Arrays.asList(
                        new Light("3", true)
                ),
                Arrays.asList(
                        new Door(false, "2")
                ),
                "bathroom");
        Room bedroom = new Room(
                Arrays.asList(
                        new Light("4", false),
                        new Light("5", false),
                        new Light("6", false)
                ),
                Arrays.asList(
                        new Door(true, "3")
                ),
                "bedroom");
        Room hall = new Room(
                Arrays.asList(
                        new Light("7", false),
                        new Light("8", false),
                        new Light("9", false)
                ),
                Arrays.asList(
                        new Door(false, "4")
                ),
                "hall");
        return new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall), new Alarm());
    }
}
