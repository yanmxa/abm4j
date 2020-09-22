package com.yanm.gol.components.editor;

import com.yanm.app.command.UndoableCommand;

public interface UndoableEditorCommand extends UndoableCommand<EditorState> {
    @Override
    void undo(EditorState state);

     @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
