package com.yanm.gol.components.simulator;

import com.yanm.ApplicationContext;
import com.yanm.gol.ApplicationComponent;
import com.yanm.gol.components.board.BoardState;
import com.yanm.gol.model.Board;
import com.yanm.gol.model.BoundedBoard;
import com.yanm.gol.components.editor.EditorState;

public class SimulatorApplicationComponent implements ApplicationComponent {

    @Override
    public void initComponent(ApplicationContext context) {

        SimulatorState simulatorState = context.getStateRegistry().getState(SimulatorState.class);
        EditorState editorState = context.getStateRegistry().getState(EditorState.class);
        BoardState boardState = context.getStateRegistry().getState(BoardState.class);

        Simulator simulator = new Simulator(simulatorState, context.getCommandExecutor());
        context.getEventBus().listenFor(SimulatorEvent.class, simulator::handle);

        editorState.getEditorBoard().listen(simulatorState.getBoard()::set);

        simulatorState.getBoard().listen(simulationBoard -> {
            if (simulatorState.getSimulating().get()) boardState.getBoard().set(simulationBoard);
        });
    }

    @Override
    public void initState(ApplicationContext context) {
        Board board = new BoundedBoard(context.getBoardWidth(), context.getBoardHeight());
        SimulatorState simulatorState = new SimulatorState(board);
        context.getStateRegistry().registry(SimulatorState.class, simulatorState);
    }
}
