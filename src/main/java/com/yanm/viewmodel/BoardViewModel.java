package com.yanm.viewmodel;

import com.yanm.model.Board;
import com.yanm.common.Property;
import com.yanm.model.CellPosition;

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
