package com.yanm.logic.editor;

import com.yanm.logic.ApplicationState;
import com.yanm.model.CellPosition;
import com.yanm.state.EditorState;

public class Editor {

    private EditorState state;

    private boolean drawingEnabled = true;

    public Editor(EditorState state) {
        this.state = state;
    }

    public void handle(DrawModeEvent event) {
        DrawModeCommand command = new DrawModeCommand(event.getDrawMode());
        command.execute(state);
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case CURSOR_PRESSED:
                boardPressed(boardEvent.getCursorPosition());
                break;
            case CURSOR_MOVED:
                cursorPositionChanged(boardEvent.getCursorPosition());
                break;
        }
    }


    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (drawingEnabled) {
            BoardEditCommand command = new BoardEditCommand(cursorPosition, state.getDrawMode().get());
            command.execute(state);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        // define a anonymous class to implement EditorCommand, and new an instance command
        EditorCommand command = (state) -> {
            state.getCursorPosition().set(cursorPosition);
        };
        command.execute(state);
    }


}
