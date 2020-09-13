package com.yanm.common.event;

import java.util.*;

public class EventBus {

    private Map<Class, List<EventListener>> listeners = new HashMap<>();

    public void emit(Event event) {
        Class eventClass = event.getClass();
        List<EventListener> eventListeners = listeners.get(eventClass);
        for (EventListener eventListener : eventListeners) {
            eventListener.handle(event);
        }
    }

    public <E extends Event> void listenFor(Class<E> eventClass, EventListener<E> listener) {
        if (!listeners.containsKey(eventClass)) listeners.put(eventClass, new LinkedList<>());
        listeners.get(eventClass).add(listener);
    }
}
