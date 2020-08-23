package com.yanm.viewmodel;

import com.yanm.model.Board;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {

    private Board board;
    private List<SimpleChangeListener<Board>> boardListeners;

    public BoardViewModel() {
        boardListeners = new LinkedList<>();
    }

    public void listenToBoard(SimpleChangeListener<Board> listener) {
        boardListeners.add(listener);
    }

    public void setBoard(Board board) {
        this.board = board;
        notifyBoardListeners();
    }

    private void notifyBoardListeners() {
        for (SimpleChangeListener<Board> boardListener : boardListeners) {
            boardListener.valueChanged(this.board);
        }
    }

    public Board getBoard() {
        return board;
    }
}
