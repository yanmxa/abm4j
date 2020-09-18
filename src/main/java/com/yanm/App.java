package com.yanm;

import com.yanm.common.event.EventBus;
import com.yanm.logic.*;
import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.state.EditorState;
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

        ApplicationStateManager appStateManager = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(20, 12);

        EditorState editorState = new EditorState(initialBoard);
        Editor editor = new Editor(editorState);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editorState.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        Simulator simulator = new Simulator(appStateManager);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editorState.getEditorBoard().listen(editorBoard -> {
            simulator.getInitialBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });
        simulator.getCurrentBoard().listen(simlationBoard -> {
            boardViewModel.getBoard().set(simlationBoard);
        });

        appStateManager.getApplicationState().listen(editor::onAppStateChanged);
        appStateManager.getApplicationState().listen(newState -> {
            if (newState == ApplicationState.EDITING) {
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
