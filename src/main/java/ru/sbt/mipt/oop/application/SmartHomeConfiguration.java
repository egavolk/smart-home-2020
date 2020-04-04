package ru.sbt.mipt.oop.application;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.smarthome.commands.DummyCommandSender;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.ccadapter.EventHandlerToCCAdapter;
import ru.sbt.mipt.oop.smarthome.events.decorators.IgnoringDecorator;
import ru.sbt.mipt.oop.smarthome.events.decorators.SMSNotifyingDecorator;
import ru.sbt.mipt.oop.smarthome.events.handlers.*;
import ru.sbt.mipt.oop.smarthome.services.logger.ConsoleLogger;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;
import ru.sbt.mipt.oop.smarthome.services.reader.SmartHomeJsonReader;
import ru.sbt.mipt.oop.smarthome.services.reader.SmartHomeReader;

import java.util.List;
import java.util.Map;

@Configuration
public class SmartHomeConfiguration {
    @Bean
    SmartHomeReader reader() {
        return new SmartHomeJsonReader("smart-home-1.json");
    }

    @Bean
    Alarm alarm() {
        return new Alarm();
    }

    @Bean
    Logger logger() {
        return new ConsoleLogger();
    }

    @Bean
    SmartHome smartHome() {
        SmartHome smartHome = reader().read();
        smartHome.setAlarm(alarm());
        return smartHome;
    }

    @Bean
    Map<String, EventType> getTypeByCCString() {
        return Map.ofEntries(
                Map.entry("LightIsOn", EventType.LIGHT_ON),
                Map.entry("LightIsOff", EventType.LIGHT_OFF),
                Map.entry("DoorIsOpen", EventType.DOOR_OPEN),
                Map.entry("DoorIsClosed", EventType.DOOR_CLOSED),
                Map.entry("DoorIsLocked", EventType.UNKNOWN),
                Map.entry("DoorIsUnlocked", EventType.UNKNOWN)
        );
    }

    @Bean
    SensorEventsManager eventManager(List<EventHandler> handlers) {
        SensorEventsManager manager = new SensorEventsManager();
        for (EventHandler handler : handlers) {
            manager.registerEventHandler(new EventHandlerToCCAdapter(handler, getTypeByCCString()));
        }
        return manager;
    }

    @Bean
    EventHandler alarmHandler() {
        return new SMSNotifyingDecorator(new AlarmStateEventHandler(smartHome(), logger()), alarm());
    }

    @Bean
    EventHandler lightHandler() {
        return new IgnoringDecorator(new LightEventHandler(smartHome(), logger()), alarm());
    }

    @Bean
    EventHandler doorHandler() {
        return new IgnoringDecorator(new DoorEventHandler(smartHome(), logger()), alarm());
    }

    @Bean
    EventHandler hallDoorHandler() {
        return new IgnoringDecorator(
                new HallDoorClosedEventHandler(smartHome(), new DummyCommandSender(), logger()), alarm()
        );
    }
}
