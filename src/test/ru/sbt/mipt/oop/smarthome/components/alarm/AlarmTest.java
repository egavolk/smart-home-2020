package ru.sbt.mipt.oop.smarthome.components.alarm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlarmTest {
    private Alarm alarm;

    @Before
    public void setUp() {
        alarm = new Alarm();
    }

    @Test
    public void activateWhenDeactivated() {
        alarm.setState(new AlarmActivatedState(alarm, "code"));
        assertTrue(alarm.getState() instanceof AlarmActivatedState);
    }

    @Test
    public void activateWhenActivated() {
        alarm.setState(new AlarmActivatedState(alarm, "code"));
        alarm.activate("code1");
        assertTrue(alarm.getState() instanceof AlarmActivatedState);
    }

    @Test
    public void activateWhenAlerted() {
        alarm.setState(new AlarmAlertedState(alarm, "code"));
        alarm.activate("code1");
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }

    @Test
    public void deactivateWhenActivatedCorrectCode() {
        alarm.setState(new AlarmActivatedState(alarm, "code"));
        alarm.deactivate("code");
        assertTrue(alarm.getState() instanceof AlarmDeactivatedState);
    }

    @Test
    public void deactivateWhenActivatedWithIncorrectCode() {
        alarm.setState(new AlarmActivatedState(alarm, "code"));
        alarm.deactivate("code1");
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }

    @Test
    public void deactivateWhenAlertedCorrectCode() {
        alarm.setState(new AlarmAlertedState(alarm, "code"));
        alarm.deactivate("code");
        assertTrue(alarm.getState() instanceof AlarmDeactivatedState);
    }

    @Test
    public void deactivateWhenAlertedIncorrectCode() {
        alarm.setState(new AlarmAlertedState(alarm, "code"));
        alarm.deactivate("code1");
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }

    @Test
    public void alertWhenActivated() {
        alarm.setState(new AlarmActivatedState(alarm, "code"));
        alarm.alert();
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }

    @Test
    public void alertWhenDeactivated() {
        alarm.setState(new AlarmDeactivatedState(alarm));
        alarm.alert();
        assertTrue(alarm.getState() instanceof AlarmDeactivatedState);
    }

    @Test
    public void alertWhenAlerted() {
        alarm.setState(new AlarmAlertedState(alarm, "code"));
        alarm.alert();
        assertTrue(alarm.getState() instanceof AlarmAlertedState);
    }
}