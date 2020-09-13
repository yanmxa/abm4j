package com.yanm.viewmodel;

import com.yanm.common.Property;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;
import javafx.scene.layout.Priority;

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
