package com.yanm.state;

import com.yanm.common.Property;
import com.yanm.model.Board;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;

public class EditorState {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();
    private Property<Board> editorBoard = new Property<>();

    public EditorState(Board board) {
        editorBoard.set(board);
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Property<Board> getEditorBoard() {
        return editorBoard;
    }
}
