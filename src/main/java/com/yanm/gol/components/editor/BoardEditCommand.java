package com.yanm.gol.components.editor;

import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;

public class BoardEditCommand implements UndoableEditorCommand {

    private CellPosition position;
    private CellState drawMode;
    private CellState prevState;

    public BoardEditCommand(CellPosition position, CellState drawMode, CellState prevState) {
        this.position = position;
        this.drawMode = drawMode;
        this.prevState = prevState;
    }

    @Override
    public void undo(EditorState editorState) {
        Board board = editorState.getEditorBoard().get();
        board.setState(position.getX(), position.getY(), prevState);
        editorState.getEditorBoard().set(board);
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getEditorBoard().get();
        board.setState(position.getX(), position.getY(), drawMode);
        editorState.getEditorBoard().set(board);
    }
}
