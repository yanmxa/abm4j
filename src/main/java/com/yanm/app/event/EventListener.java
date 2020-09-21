package com.yanm.app.event;

public interface EventListener<T extends Event> {

    void handle(T event);

}
