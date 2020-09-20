package com.yanm.state;

import com.yanm.common.Property;
import com.yanm.model.Board;

public class SimulatorState {

    private Property<Board> board = new Property<>();

    public SimulatorState(Board board) {
        this.board.set(board);
    }

    public Property<Board> getBoard() {
        return board;
    }
}
