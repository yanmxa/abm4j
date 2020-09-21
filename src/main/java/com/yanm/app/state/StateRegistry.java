package com.yanm.app.state;

import java.util.HashMap;
import java.util.Map;

public class StateRegistry {

    private Map<Class<?>, Object> states = new HashMap<>();

    public <T> void registry(Class<T> stateClass, T state) {
        states.put(stateClass, state);
    }

    public <T> T getState(Class<T> stateClass) {
        return (T) states.get(stateClass);
    }
}
