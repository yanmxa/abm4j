package com.yanm.logic.simulator;

import com.yanm.command.CommandExecutor;
import com.yanm.logic.ApplicationState;
import com.yanm.logic.ApplicationStateManager;
import com.yanm.model.Simulation;
import com.yanm.model.StandardRule;
import com.yanm.state.SimulatorState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private ApplicationStateManager applicationStateManager;

    private SimulatorState state;
    private CommandExecutor commandExecutor;
    private boolean reset = true;

    public Simulator(ApplicationStateManager applicationStateManager, SimulatorState state, CommandExecutor commandExecutor) {
        this.applicationStateManager = applicationStateManager;
        this.state = state;
        this.commandExecutor = commandExecutor;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> step()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handle(SimulatorEvent event) {
        switch (event.getEventType()) {
            case START:
                start();
                break;
            case STOP:
                stop();
                break;
            case STEP:
                step();
                break;
            case RESET:
                reset();
                break;
        }
    }

    private void step() {
        if (reset) {
            reset = false;
            simulation = new Simulation(state.getBoard().get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }

        simulation.step();

        SimulatorCommond command = (state) -> state.getBoard().set(simulation.getBoard());
        commandExecutor.execute(command);
    }

    private void start() {
        this.timeline.play();
    }

    private void stop() {
        this.timeline.stop();
    }

    private void reset() {
        reset = true;
        applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }

}
