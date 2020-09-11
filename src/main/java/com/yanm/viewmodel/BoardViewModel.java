package com.yanm.viewmodel;

import com.yanm.model.Board;
import com.yanm.common.Property;

import java.util.List;

public class BoardViewModel {

    private Property<Board> board = new Property<>();
    private List<SimpleChangeListener<Board>> boardListeners;

    public BoardViewModel() {
    }

    public Property<Board> getBoard() {
        return board;
    }
}
