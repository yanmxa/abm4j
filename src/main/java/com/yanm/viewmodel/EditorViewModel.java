package com.yanm.viewmodel;

import com.yanm.model.Board;
import com.yanm.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {

    private CellState drawMode = CellState.ALIVE;
    private List<SimpleChangeListener<CellState>> drawListeners;

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        editorBoard = initialBoard;
        drawListeners = new LinkedList<>();
    }


    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            boardViewModel.setBoard(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> listener) {
        drawListeners.add(listener);
    }

    public void setDrawMode(CellState drawMode) {
        this.drawMode = drawMode;
        notifyDrawModeListeners();
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawListener : drawListeners) {
            drawListener.valueChanged(this.drawMode);
        }
    }

    public void boardPressed(int simX, int simY) {
        if (drawingEnabled) {
            editorBoard.setState(simX, simY, drawMode);
            boardViewModel.setBoard(editorBoard);
        }
    }
}
