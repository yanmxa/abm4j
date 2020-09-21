package com.yanm.gol.state;

import com.yanm.app.observable.Property;
import com.yanm.gol.model.Board;

public class SimulatorState {

    private Property<Board> board = new Property<>();

    public SimulatorState(Board board) {
        this.board.set(board);
    }

    public Property<Board> getBoard() {
        return board;
    }
}
