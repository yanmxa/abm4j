package com.yanm.gol.components.editor;

import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;

public class Change {
    private CellPosition position;
    private CellState newState;
    private CellState prevState;

    public Change(CellPosition position, CellState newState, CellState prevState) {
        this.position = position;
        this.newState = newState;
        this.prevState = prevState;
    }

    public CellPosition getPostition() {
        return position;
    }

    public CellState getNewState() {
        return newState;
    }

    public CellState getPrevState() {
        return prevState;
    }
}
