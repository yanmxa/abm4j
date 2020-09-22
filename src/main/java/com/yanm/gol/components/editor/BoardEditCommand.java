package com.yanm.gol.components.editor;

import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellPosition;

public class BoardEditCommand implements UndoableEditorCommand {


    private Edits edits;

    public BoardEditCommand(Edits edits) {
        this.edits = new Edits(edits);
    }

    @Override
    public void undo(EditorState editorState) {
        Board board = editorState.getEditorBoard().get();

        for (Change edit : edits) {
            CellPosition position = edit.getPostition();
            board.setState(position.getX(), position.getY(), edit.getPrevState());
        }

        editorState.getEditorBoard().set(board);
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getEditorBoard().get();

        for (Change edit : edits) {
            CellPosition position = edit.getPostition();
            board.setState(position.getX(), position.getY(), edit.getNewState());
        }

        editorState.getEditorBoard().set(board);
    }
}
