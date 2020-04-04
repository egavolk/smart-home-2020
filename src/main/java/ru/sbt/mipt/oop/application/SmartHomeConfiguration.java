package ru.sbt.mipt.oop.application;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.smarthome.commands.DummyCommandSender;
import ru.sbt.mipt.oop.smarthome.components.SmartHome;
import ru.sbt.mipt.oop.smarthome.components.alarm.Alarm;
import ru.sbt.mipt.oop.smarthome.events.EventType;
import ru.sbt.mipt.oop.smarthome.events.ccadapter.EventHandlerToCCAdapter;
import ru.sbt.mipt.oop.smarthome.events.decorators.IgnoringDecorator;
import ru.sbt.mipt.oop.smarthome.events.decorators.SMSNotifyingDecorator;
import ru.sbt.mipt.oop.smarthome.events.handlers.*;
import ru.sbt.mipt.oop.smarthome.remotecontrol.RemoteController;
import ru.sbt.mipt.oop.smarthome.remotecontrol.command.*;
import ru.sbt.mipt.oop.smarthome.services.logger.ConsoleLogger;
import ru.sbt.mipt.oop.smarthome.services.logger.Logger;
import ru.sbt.mipt.oop.smarthome.services.reader.SmartHomeJsonReader;
import ru.sbt.mipt.oop.smarthome.services.reader.SmartHomeReader;

import java.util.*;

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

    @Bean
    Command alarmAlertCommand() {
        return new AlarmAlertCommand(alarm());
    }

    @Bean
    Command alarmActivateCommand() {
        return new AlarmActivateCommand(alarm(), "code");
    }

    @Bean
    Command allLightTurnOnCommand() {
        return new AllLightCommand(smartHome(), true);
    }

    @Bean
    Command allLightTurnOffCommand() {
        return new AllLightCommand(smartHome(), false);
    }

    @Bean
    Command hallDoorOpenCommand() {
        return new HallDoorCommand(smartHome(), true);
    }

    @Bean
    Command hallDoorCloseCommand() {
        return new HallDoorCommand(smartHome(), false);
    }

    @Bean
    Command hallLightTurnOnCommand() {
        return new HallLightCommand(smartHome(), true);
    }

    @Bean
    Command hallLightTurnOffCommand() {
        return new HallLightCommand(smartHome(), false);
    }

    @Bean
    Map<String, Command> rcCommandMap() {
        return Map.ofEntries(
                Map.entry("A", alarmAlertCommand()),
                Map.entry("B", alarmActivateCommand()),
                Map.entry("C", allLightTurnOnCommand()),
                Map.entry("D", allLightTurnOffCommand()),
                Map.entry("1", hallDoorOpenCommand()),
                Map.entry("2", hallDoorCloseCommand()),
                Map.entry("3", hallLightTurnOnCommand()),
                Map.entry("4", hallLightTurnOffCommand())
        );
    }

    @Bean
    RemoteControl remoteController() {
        return new RemoteController(rcCommandMap());
    }

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry rcRegistry = new RemoteControlRegistry();
        rcRegistry.registerRemoteControl(remoteController(), "rc_1");
        return rcRegistry;
    }
}
