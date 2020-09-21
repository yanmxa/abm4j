package com.yanm.gol.logic.editor;

import com.yanm.app.command.Command;
import com.yanm.gol.state.EditorState;

public interface EditorCommand extends Command<EditorState> {

    @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
