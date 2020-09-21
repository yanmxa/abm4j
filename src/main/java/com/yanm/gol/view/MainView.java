package com.yanm.gol.view;

import com.yanm.app.event.EventBus;
import com.yanm.gol.components.editor.DrawModeEvent;
import com.yanm.gol.model.CellState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EventBus eventBus;

    private SimulationCanvas canvas;

    public MainView(EventBus eventBus) {
        this.eventBus = eventBus;

        canvas = new SimulationCanvas(eventBus);
        this.setCenter(canvas);

        Toolbar toolBar = new Toolbar(eventBus);
        this.setTop(toolBar);

        this.setOnKeyPressed(this::onKeyPressed);
    }

    public void addDrawLayer(DrawLayer drawLayer) {
        canvas.addDrawLayer(drawLayer);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            eventBus.emit(new DrawModeEvent(CellState.ALIVE));
        } else if (keyEvent.getCode() == KeyCode.E) {
            eventBus.emit(new DrawModeEvent(CellState.DEAD));
        }

    }






}
