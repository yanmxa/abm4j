package com.yanm.viewmodel;

import com.yanm.common.Property;
import com.yanm.model.Board;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;

public class EditorViewModel {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        editorBoard = initialBoard;
    }


    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            boardViewModel.getBoard().set(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }


    public void boardPressed(CellPosition cellPosition) {
        if (drawingEnabled) {
            editorBoard.setState(cellPosition.getX(), cellPosition.getY(), drawMode.get());
            boardViewModel.getBoard().set(editorBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }
}
