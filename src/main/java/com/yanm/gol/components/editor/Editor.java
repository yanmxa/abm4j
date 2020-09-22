package com.yanm.gol.components.editor;

import com.yanm.app.command.CommandExecutor;
import com.yanm.gol.components.simulator.SimulatorEvent;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;

public class Editor {

    private EditorState state;
    private CommandExecutor commandExecutor;

    private boolean drawingEnabled = true;

    public Editor(EditorState state, CommandExecutor commandExecutor) {
        this.state = state;
        this.commandExecutor = commandExecutor;
    }

    public void handle(DrawModeEvent event) {
        DrawModeCommand command = new DrawModeCommand(event.getDrawMode());
        commandExecutor.execute(command);
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


    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START || event.getEventType() == SimulatorEvent.Type.STEP){
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (drawingEnabled) {
            CellState currentState = this.state.getEditorBoard().get().getState(cursorPosition.getX(), cursorPosition.getY());
            CellState newState = this.state.getDrawMode().get();

            if (currentState != newState) {
                BoardEditCommand command = new BoardEditCommand(cursorPosition, newState, currentState);
                commandExecutor.execute(command);
            }
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        // define a anonymous class to implement EditorCommand, and new an instance command
        EditorCommand command = (state) -> state.getCursorPosition().set(cursorPosition);
        commandExecutor.execute(command);
    }


}
