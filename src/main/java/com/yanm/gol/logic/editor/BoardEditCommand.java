package com.yanm.gol.logic.editor;

import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;
import com.yanm.gol.state.EditorState;

public class BoardEditCommand implements EditorCommand {

    private CellPosition position;
    private CellState drawMode;

    public BoardEditCommand(CellPosition position, CellState drawMode) {
        this.position = position;
        this.drawMode = drawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getEditorBoard().get();
        board.setState(position.getX(), position.getY(), drawMode);
        editorState.getEditorBoard().set(board);
    }
}
