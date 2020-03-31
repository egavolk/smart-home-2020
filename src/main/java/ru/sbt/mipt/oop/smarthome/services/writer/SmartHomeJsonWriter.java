package ru.sbt.mipt.oop.smarthome.services.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmartHomeJsonWriter implements SmartHomeWriter {
    private String name;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SmartHomeJsonWriter(String name) {
        this.name = name;
    }

    @Override
    public void write(SmartHome smartHome) {
        String jsonString = gson.toJson(smartHome);
        System.out.println(jsonString);
        Path path = Paths.get(name);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException("Could not read SmartHome from " + name, e);
        }
    }
}
