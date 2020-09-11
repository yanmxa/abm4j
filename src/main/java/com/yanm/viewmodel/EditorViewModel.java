package com.yanm.viewmodel;

import com.yanm.common.Property;
import com.yanm.model.Board;
import com.yanm.model.CellState;

public class EditorViewModel {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);

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


    public void boardPressed(int simX, int simY) {
        if (drawingEnabled) {
            editorBoard.setState(simX, simY, drawMode.get());
            boardViewModel.getBoard().set(editorBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }
}
