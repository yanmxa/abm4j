package com.yanm;

import com.yanm.common.event.EventBus;
import com.yanm.logic.*;
import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.view.Infobar;
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

        Editor editor = new Editor(initialBoard);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editor.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        Simulator simulator = new Simulator(appStateManager);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editor.getBoard().listen(editorBoard -> {
            simulator.getInitialBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });
        simulator.getCurrentBoard().listen(simlationBoard -> {
            boardViewModel.getBoard().set(simlationBoard);
        });

        appStateManager.getApplicationState().listen(editor::onAppStateChanged);

        boardViewModel.getBoard().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editor.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCusorPosition().set(cursorPosition);
        });
        editor.getDrawMode().listen(drawMode -> {
            infoBarViewModel.getCurrentDrawMode().set(drawMode);
        });
        Infobar infobar = new Infobar(infoBarViewModel);

        MainView mainView = new MainView(eventBus);
        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infobar);

        Scene scene = new Scene(mainView, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
