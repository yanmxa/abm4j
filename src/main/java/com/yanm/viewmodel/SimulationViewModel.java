package com.yanm.viewmodel;

import com.yanm.Simulation;
import com.yanm.model.StandardRule;
import com.yanm.viewmodel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class SimulationViewModel {

    private Timeline timeline;
    private Simulation simulation;
    private BoardViewModel boardViewModel;

    public SimulationViewModel(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.SIMULATING) {
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
