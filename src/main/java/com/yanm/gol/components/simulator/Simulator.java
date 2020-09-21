package com.yanm.gol.components.simulator;

import com.yanm.app.command.CommandExecutor;
import com.yanm.gol.model.Simulation;
import com.yanm.gol.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;

    private SimulatorState state;
    private CommandExecutor commandExecutor;
    private boolean reset = true;

    public Simulator(SimulatorState state, CommandExecutor commandExecutor) {
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
            state.getSimulating().set(true);
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
        state.getSimulating().set(false);
    }

}
