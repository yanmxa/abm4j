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
        cursorPositionChanged(boardEvent.getCursorPosition());

        switch (boardEvent.getEventType()) {
            case PRESSED:
                beginEdit();
                handleEdit(boardEvent.getCursorPosition());
                break;
            case CURSOR_MOVED:
                if(state.getEditInProgress().get()){
                    handleEdit(boardEvent.getCursorPosition());
                }
                break;
            case RELEASED:
                handleEdit(boardEvent.getCursorPosition());
                endEdit();
                break;
        }
    }

    private void beginEdit() {
        state.getEditInProgress().set(true);
        state.getCurrentEdit().set(new Edits());
    }

    private void endEdit() {
        BoardEditCommand editCommand = new BoardEditCommand(state.getCurrentEdit().get());
        commandExecutor.execute(editCommand);
        state.getEditInProgress().set(false);
        state.getCurrentEdit().set(null);
    }


    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START || event.getEventType() == SimulatorEvent.Type.STEP){
            drawingEnabled = false;
        }
    }

    private void handleEdit(CellPosition cursorPosition) {
        if (drawingEnabled) {
            CellState currentState = this.state.getEditorBoard().get().getState(cursorPosition.getX(), cursorPosition.getY());
            CellState newState = this.state.getDrawMode().get();

            Change change = new Change(cursorPosition, newState, currentState);

            state.getCurrentEdit().get().add(change);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        // define a anonymous class to implement EditorCommand, and new an instance command
        EditorCommand command = (state) -> state.getCursorPosition().set(cursorPosition);
        commandExecutor.execute(command);
    }


}
