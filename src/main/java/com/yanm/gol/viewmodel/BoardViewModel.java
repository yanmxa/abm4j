package com.yanm.gol.viewmodel;

import com.yanm.gol.model.Board;
import com.yanm.app.observable.Property;
import com.yanm.gol.model.CellPosition;

public class BoardViewModel {

    private Property<Board> board = new Property<>();
    private Property<CellPosition> cursorPosition = new Property<>();

    public BoardViewModel() {
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Property<Board> getBoard() {
        return board;
    }
}
