package com.yanm.view;

import com.yanm.model.CellPosition;
import com.yanm.model.CellState;
import com.yanm.logic.Editor;
import com.yanm.viewmodel.InfoBarViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class Infobar extends HBox {

    private static String drawModeFormat = "Draw Mode: %s";
    private static String cusorPosFormat = "Cursor: (%s, %s)";

    private Label cursor;
    private Label editingTool;

    public Infobar(InfoBarViewModel infoBarViewModel) {
        this.cursor = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        infoBarViewModel.getCurrentDrawMode().listen(this::setDrawMode);
        infoBarViewModel.getCusorPosition().listen(this::setCursorPosition);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0 );
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    private void setDrawMode(CellState drawMode) {
        String drawModeStr = drawMode == CellState.ALIVE ? "Drawing" : "Erasing";
        this.editingTool.setText(String.format(drawModeFormat, drawModeStr));
    }

    private void setCursorPosition(CellPosition cursorPosition) {
        this.cursor.setText(String.format(cusorPosFormat, cursorPosition.getX(), cursorPosition.getY()));
    }
}
