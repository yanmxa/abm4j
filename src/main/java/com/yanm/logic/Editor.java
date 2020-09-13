package com.yanm.logic;

import com.yanm.common.Property;
import com.yanm.model.Board;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;

public class Editor {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();
    private Property<Board> editorBoard = new Property<>();

    private boolean drawingEnabled = true;

    public Editor(Board initialBoard) {
        editorBoard.set(initialBoard);
    }


    public void handle(DrawModeEvent event) {
        this.drawMode.set(event.getDrawMode());
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case CURSOR_PRESSED:
                boardPressed(boardEvent.getCursorPosition());
                break;
            case CURSOR_MOVED:
                cursorPosition.set(boardEvent.getCursorPosition());
                break;
        }
    }


    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            editorBoard.set(editorBoard.get());
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cellPosition) {
        cursorPosition.set(cellPosition);
        if (drawingEnabled) {
            Board board = editorBoard.get();
            board.setState(cellPosition.getX(), cellPosition.getY(), drawMode.get());
            editorBoard.set(board);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Property<Board> getBoard() {
        return editorBoard;
    }
}
