package com.yanm.gol.components.infobar;

import com.yanm.app.observable.Property;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;

public class InfoBarViewModel {

    private Property<CellState> currentDrawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cusorPosition = new Property<>();

    public Property<CellState> getCurrentDrawMode() {
        return currentDrawMode;
    }

    public Property<CellPosition> getCusorPosition() {
        return cusorPosition;
    }
}
