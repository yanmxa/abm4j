package com.yanm.logic;

import com.yanm.common.Property;
import com.yanm.model.Board;
import com.yanm.model.Simulation;
import com.yanm.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private ApplicationStateManager applicationStateManager;

    private Property<Board> initialBoard = new Property<>();
    private Property<Board> currentBoard = new Property<>();

    public Simulator(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;

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
        if (applicationStateManager.getApplicationState().get() != ApplicationState.SIMULATING) {
            simulation = new Simulation(initialBoard.get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }

        simulation.step();
        currentBoard.set(simulation.getBoard());
    }

    private void start() {
        this.timeline.play();
    }

    private void stop() {
        this.timeline.stop();
    }

    private void reset() {
        applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }

    public Property<Board> getInitialBoard() {
        return initialBoard;
    }

    public Property<Board> getCurrentBoard() {
        return currentBoard;
    }
}
