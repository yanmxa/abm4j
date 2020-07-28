package com.yanm;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Infobar extends HBox {

    private static String drawModeFormat = "Draw Mode: %s";
    private static String cusorPosFormat = "Cursor: (%s, %s)";

    private Label cursor;
    private Label editingTool;

    public Infobar() {

        this.cursor = new Label("Cursor: (0, 0)");
        this.editingTool = new Label("Draw Mode: Drawing");

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0 );
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    public void setDrawMode(int drawMode) {
        String drawModeStr = drawMode == Simulation.ALIVE ? "Drawing" : "Erasing";
        this.editingTool.setText(String.format(drawModeFormat, drawModeStr));
    }

    public void setCursorPosition(int x, int y) {
        this.cursor.setText(String.format(cusorPosFormat, x, y));
    }
}
