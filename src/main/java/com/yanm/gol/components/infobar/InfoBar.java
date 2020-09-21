package com.yanm.gol.components.infobar;

import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {

    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPosFormat = "Cursor: (%s, %s)";

    private Label cursorLabel;
    private Label editingLabel;

    public InfoBar(InfoBarViewModel infoBarViewModel) {
        cursorLabel = new Label("Cursor: (0, 0)");
        editingLabel = new Label("Draw Mode: Drawing");

        infoBarViewModel.getCurrentDrawMode().listen(this::setDrawMode);
        infoBarViewModel.getCusorPosition().listen(this::setCursorPosition);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0 );
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingLabel, spacer, this.cursorLabel);
    }

    private void setDrawMode(CellState drawMode) {
        String drawModeStr = drawMode == CellState.ALIVE ? "Drawing" : "Erasing";
        editingLabel.setText(String.format(drawModeFormat, drawModeStr));
    }

    private void setCursorPosition(CellPosition cursorPosition) {
        cursorLabel.setText(String.format(cursorPosFormat, cursorPosition.getX(), cursorPosition.getY()));
    }
}
