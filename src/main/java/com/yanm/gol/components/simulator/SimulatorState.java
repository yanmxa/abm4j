package com.yanm.gol.components.simulator;

import com.yanm.app.observable.Property;
import com.yanm.gol.model.Board;

public class SimulatorState {

    private Property<Board> board = new Property<>();
    private Property<Boolean> simulating = new Property<>(false);

    public SimulatorState(Board board) {
        this.board.set(board);
    }

    public Property<Board> getBoard() {
        return board;
    }

    public Property<Boolean> getSimulating() {
        return simulating;
    }
}
