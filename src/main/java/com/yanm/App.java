package com.yanm;

import com.yanm.app.command.CommandExecutor;
import com.yanm.app.event.EventBus;
import com.yanm.gol.logic.*;
import com.yanm.gol.logic.editor.BoardEvent;
import com.yanm.gol.logic.editor.DrawModeEvent;
import com.yanm.gol.logic.editor.Editor;
import com.yanm.gol.logic.simulator.Simulator;
import com.yanm.gol.logic.simulator.SimulatorEvent;
import com.yanm.gol.model.Board;
import com.yanm.gol.model.BoundedBoard;
import com.yanm.gol.state.EditorState;
import com.yanm.gol.state.SimulatorState;
import com.yanm.app.state.StateRegistry;
import com.yanm.gol.view.InfoBar;
import com.yanm.gol.view.MainView;
import com.yanm.gol.view.SimulationCanvas;
import com.yanm.gol.view.Toolbar;
import com.yanm.gol.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(stateRegistry);

        ApplicationStateManager appViewModel = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        EditorState editorState = new EditorState(initialBoard);
        stateRegistry.registry(EditorState.class, editorState);
        Editor editor = new Editor(editorState, commandExecutor);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editorState.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        SimulatorState simulatorState = new SimulatorState(initialBoard);
        stateRegistry.registry(SimulatorState.class, simulatorState);
        Simulator simulator = new Simulator(appViewModel, simulatorState, commandExecutor);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editorState.getEditorBoard().listen(editorBoard -> {
            simulatorState.getBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });
        simulatorState.getBoard().listen(simlationBoard -> {
            boardViewModel.getBoard().set(simlationBoard);
        });

        appViewModel.getApplicationState().listen(editor::onAppStateChanged);
        appViewModel.getApplicationState().listen(newState -> {
            if (newState == ApplicationState.EDITING) {
                simulatorState.getBoard().set(editorState.getEditorBoard().get());
                boardViewModel.getBoard().set(editorState.getEditorBoard().get());
            }
        });

        boardViewModel.getBoard().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editorState.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCusorPosition().set(cursorPosition);
        });
        editorState.getDrawMode().listen(drawMode -> {
            infoBarViewModel.getCurrentDrawMode().set(drawMode);
        });
        InfoBar infoBar = new InfoBar(infoBarViewModel);

        MainView mainView = new MainView(eventBus);
        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
