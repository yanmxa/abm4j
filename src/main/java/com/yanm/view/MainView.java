package com.yanm.view;

import com.yanm.common.event.EventBus;
import com.yanm.logic.DrawModeEvent;
import com.yanm.model.CellState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EventBus eventBus;

    public MainView(EventBus eventBus) {
        this.eventBus = eventBus;
        this.setOnKeyPressed(this::onKeyPressed);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            eventBus.emit(new DrawModeEvent(CellState.ALIVE));
        } else if (keyEvent.getCode() == KeyCode.E) {
            eventBus.emit(new DrawModeEvent(CellState.DEAD));
        }

    }






}