package com.yanm;

import com.yanm.model.CellState;
import com.yanm.viewmodel.EditorViewModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EditorViewModel editorViewModel;

    public MainView(EditorViewModel editorViewModel) {

        this.editorViewModel = editorViewModel;
        this.setOnKeyPressed(this::onKeyPressed);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            editorViewModel.setDrawMode(CellState.ALIVE);
        } else if (keyEvent.getCode() == KeyCode.E) {
            editorViewModel.setDrawMode(CellState.DEAD);
        }

    }






}
