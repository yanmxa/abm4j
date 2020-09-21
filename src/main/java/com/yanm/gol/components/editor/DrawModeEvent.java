package com.yanm.gol.components.editor;

import com.yanm.app.event.Event;
import com.yanm.gol.model.CellState;

public class DrawModeEvent implements Event {

    private CellState newDrawMode;

    public DrawModeEvent(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    public CellState getDrawMode() {
        return newDrawMode;
    }
}
