package com.yanm.logic.simulator;

import com.yanm.common.event.Event;

public class SimulatorEvent implements Event {

    public enum Type {
        START,
        STOP,
        STEP,
        RESET
    }

    private Type eventType;

    public SimulatorEvent(Type eventType) {
        this.eventType = eventType;
    }

    public Type getEventType() {
        return eventType;
    }
}
