package com.yanm.logic.editor;

import com.yanm.common.event.Event;
import com.yanm.model.CellPosition;

public class BoardEvent implements Event {

    public enum Type {
        CURSOR_MOVED,
        CURSOR_PRESSED
    }

    private Type eventType;
    private CellPosition cursorPosition;

    public BoardEvent(Type eventType, CellPosition cursorPosition) {
        this.eventType = eventType;
        this.cursorPosition = cursorPosition;
    }

    public Type getEventType() {
        return eventType;
    }

    public CellPosition getCursorPosition() {
        return cursorPosition;
    }
}
