package ru.sbt.mipt.oop.application;

import ru.sbt.mipt.oop.smarthome.commands.CommandSender;
import ru.sbt.mipt.oop.smarthome.commands.DummyCommandSender;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.events.EventManager;
import ru.sbt.mipt.oop.smarthome.events.decorators.IgnoringDecorator;
import ru.sbt.mipt.oop.smarthome.events.decorators.SMSNotifyingDecorator;
import ru.sbt.mipt.oop.smarthome.events.handlers.*;
import ru.sbt.mipt.oop.smarthome.events.providers.EventProvider;
import ru.sbt.mipt.oop.smarthome.events.providers.RandomEventProvider;
import ru.sbt.mipt.oop.smarthome.services.logger.ConsoleLogger;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;
import ru.sbt.mipt.oop.smarthome.services.reader.SmartHomeJsonReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String... args) throws IOException {
        Alarm alarm = new Alarm();
        SmartHome smartHome = new SmartHomeJsonReader("smart-home-1.js").read();
        smartHome.setAlarm(alarm);
        Logger logger = new ConsoleLogger();
        CommandSender sender = new DummyCommandSender();

        List<EventHandler> handlers = Arrays.asList(
                new IgnoringDecorator(new DoorEventHandler(smartHome, logger), alarm),
                new IgnoringDecorator(new LightEventHandler(smartHome, logger), alarm),
                new IgnoringDecorator(new HallDoorClosedEventHandler(smartHome, sender, logger), alarm),
                new SMSNotifyingDecorator(new AlarmStateEventHandler(smartHome, logger), alarm)
        );

        EventProvider provider = new RandomEventProvider();
        EventManager manager = new EventManager(provider, handlers, logger);
        manager.runCycle();
    }
}
