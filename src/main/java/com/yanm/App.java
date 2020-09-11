package com.yanm;

import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.view.SimulationCanvas;
import com.yanm.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(14, 14);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, initialBoard);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.getApplicationState().listen(editorViewModel::onAppStateChanged);
        appViewModel.getApplicationState().listen(simulationViewModel::onAppStateChanged);

        boardViewModel.getBoard().set(initialBoard);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);
        Infobar infobar = new Infobar(editorViewModel);

        MainView mainView = new MainView(editorViewModel);
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
