package com.yanm.gol.view;

import com.yanm.app.event.EventBus;
import com.yanm.gol.logic.editor.DrawModeEvent;
import com.yanm.gol.logic.simulator.SimulatorEvent;
import com.yanm.gol.model.CellState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private EventBus eventBus;

    public Toolbar(EventBus eventBus) {

        this.eventBus = eventBus;

        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);

        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        Button start = new Button("Start");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleStop(ActionEvent event) {
        eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STOP));
    }

    private void handleStart(ActionEvent event) {
        eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.START));
    }

    private void handleReset(ActionEvent event) {
        eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.RESET));
    }

    private void handleStep(ActionEvent event) {
        eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STEP));
    }

    private void handleErase(ActionEvent event) {
         eventBus.emit(new DrawModeEvent(CellState.DEAD));
    }

    private void handleDraw(ActionEvent event) {
        eventBus.emit(new DrawModeEvent(CellState.ALIVE));
    }

}
