package com.yanm.logic;

import com.yanm.command.Command;
import com.yanm.state.EditorState;

public interface EditorCommand extends Command<EditorState> {

    @Override
    void execute(EditorState editorState);

}
