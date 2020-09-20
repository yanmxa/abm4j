package com.yanm.logic.editor;

import com.yanm.logic.editor.EditorCommand;
import com.yanm.model.Board;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;
import com.yanm.state.EditorState;

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
