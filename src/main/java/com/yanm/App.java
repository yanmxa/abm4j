package com.yanm;

import com.yanm.common.event.EventBus;
import com.yanm.logic.*;
import com.yanm.logic.editor.BoardEvent;
import com.yanm.logic.editor.DrawModeEvent;
import com.yanm.logic.editor.Editor;
import com.yanm.logic.simulator.Simulator;
import com.yanm.logic.simulator.SimulatorEvent;
import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.state.EditorState;
import com.yanm.state.SimulatorState;
import com.yanm.view.InfoBar;
import com.yanm.view.MainView;
import com.yanm.view.SimulationCanvas;
import com.yanm.view.Toolbar;
import com.yanm.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        EventBus eventBus = new EventBus();

        ApplicationStateManager appViewModel = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        EditorState editorState = new EditorState(initialBoard);
        Editor editor = new Editor(editorState);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editorState.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        SimulatorState simulatorState = new SimulatorState(initialBoard);
        Simulator simulator = new Simulator(appViewModel, simulatorState);
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
