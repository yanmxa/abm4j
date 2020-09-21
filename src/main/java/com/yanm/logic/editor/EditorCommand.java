package com.yanm.logic.editor;

import com.yanm.command.Command;
import com.yanm.state.EditorState;

public interface EditorCommand extends Command<EditorState> {

    @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
