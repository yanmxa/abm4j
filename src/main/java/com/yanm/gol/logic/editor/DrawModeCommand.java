package com.yanm.gol.logic.editor;

import com.yanm.gol.model.CellState;
import com.yanm.gol.state.EditorState;

public class DrawModeCommand implements EditorCommand {

    private CellState newDrawMode;

    public DrawModeCommand(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        editorState.getDrawMode().set(newDrawMode);
    }
}
