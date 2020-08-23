package com.yanm;

import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(10, 10);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, initialBoard);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.listenToAppState(editorViewModel::onAppStateChanged);
        appViewModel.listenToAppState(simulationViewModel::onAppStateChanged);

        MainView mainView = new MainView(appViewModel, boardViewModel, editorViewModel, simulationViewModel);
        boardViewModel.setBoard(initialBoard);
        Scene scene = new Scene(mainView, 640, 480);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
