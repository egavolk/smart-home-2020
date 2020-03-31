package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeJsonReader implements SmartHomeReader {
    private final Gson gson = new Gson();
    private String name;

    public SmartHomeJsonReader(String name) {
        this.name = name;
    }

    @Override
    public SmartHome read(){
        try {
            String json = new String(Files.readAllBytes(Paths.get(name)));
            return gson.fromJson(json, SmartHome.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read SmartHome from " + name, e);
        }
    }
}
