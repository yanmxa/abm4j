package com.yanm;

import com.yanm.model.CellState;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {


    private MainView mainView;

    private Simulator simulator;

    public Toolbar(MainView mainView) {
        this.mainView = mainView;

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
        simulator.stop();
    }

    private void handleStart(ActionEvent event) {
        switch2SimulatingState();
        simulator.start();
    }

    private void handleReset(ActionEvent event) {
        mainView.setApplicationState(MainView.EDITING);
        simulator = null;
        mainView.draw();
    }

    private void handleStep(ActionEvent event) {
        switch2SimulatingState();
        mainView.getSimulation().step();
        mainView.draw();
    }

    private void handleErase(ActionEvent event) {
        this.mainView.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent event) {
        this.mainView.setDrawMode(CellState.ALIVE);
    }


    private void switch2SimulatingState() {
        if (this.mainView.getApplicationState() == MainView.EDITING) {
            mainView.setApplicationState(MainView.SIMULATING);
            simulator = new Simulator(mainView.getSimulation(), mainView);
        }
    }


}
