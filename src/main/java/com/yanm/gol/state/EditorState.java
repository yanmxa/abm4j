package com.yanm.gol.state;

import com.yanm.app.observable.Property;
import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;

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
