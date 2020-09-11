package com.yanm;

import com.yanm.model.CellState;
import com.yanm.model.StandardRule;
import com.yanm.viewmodel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private EditorViewModel editorViewModel;
    private ApplicationViewModel applicationViewModel;
     private SimulationViewModel simulationViewModel;

    public Toolbar(EditorViewModel editorViewModel, ApplicationViewModel appViewModel, SimulationViewModel simulationViewModel) {
        this.editorViewModel = editorViewModel;
        this.applicationViewModel = appViewModel;
        this.simulationViewModel = simulationViewModel;

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
        simulationViewModel.stop();
    }

    private void handleStart(ActionEvent event) {
        switch2SimulatingState();
        simulationViewModel.start();
    }

    private void handleReset(ActionEvent event) {
        applicationViewModel.getApplicationState().set(ApplicationState.EDITING);
    }

    private void handleStep(ActionEvent event) {
        switch2SimulatingState();
        simulationViewModel.doStep();
    }

    private void handleErase(ActionEvent event) {
        editorViewModel.getDrawMode().set(CellState.DEAD);
    }

    private void handleDraw(ActionEvent event) {
        editorViewModel.getDrawMode().set(CellState.ALIVE);
    }


    private void switch2SimulatingState() {
        applicationViewModel.getApplicationState().set(ApplicationState.SIMULATING);
//        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
//        simulationViewModel = new SimulationViewModel(simulation, this.boardViewModel);
    }


}
