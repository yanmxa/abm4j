package com.yanm.gol.components.board;

import com.yanm.app.observable.Property;
import com.yanm.gol.model.Board;

public class BoardState {

    private Property<Board> board = new Property<>();

    public BoardState(Board board) {
        this.board.set(board);
    }

    public Property<Board> getBoard() {
        return board;
    }
}
