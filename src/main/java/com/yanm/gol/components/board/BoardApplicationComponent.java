package com.yanm.gol.components.board;

import com.yanm.ApplicationContext;
import com.yanm.gol.ApplicationComponent;
import com.yanm.gol.model.Board;
import com.yanm.gol.model.BoundedBoard;

public class BoardApplicationComponent implements ApplicationComponent {

    @Override
    public void initComponent(ApplicationContext context) {
        BoardState state = context.getStateRegistry().getState(BoardState.class);

        BoardDrawLayer boardDrawLayer = new BoardDrawLayer(state);
        GridDrawLayer gridDrawLayer = new GridDrawLayer(state);

        context.getMainView().addDrawLayer(boardDrawLayer);
        context.getMainView().addDrawLayer(gridDrawLayer);
    }

    @Override
    public void initState(ApplicationContext context) {
        Board board = new BoundedBoard(context.getBoardWidth(), context.getBoardHeight());
        BoardState boardState = new BoardState(board);
        context.getStateRegistry().registry(BoardState.class, boardState);
    }
}
