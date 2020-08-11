package com.yanm.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationViewModel {

    private  ApplicationState currentState;
    private List<SimpleChangeListener> appStateListeners;


    public ApplicationViewModel(ApplicationState currentState) {
        this.currentState = currentState;
        this.appStateListeners = new ArrayList<>();
    }

    public void listenToAppState(SimpleChangeListener listener) {
        appStateListeners.add(listener);
    }

    public void setCurrentState(ApplicationState newState) {
        if (this.currentState != newState) {
            this.currentState = newState;
            notifyAppStateListeners();
        }
    }

    private void notifyAppStateListeners() {
        for (SimpleChangeListener appStateListener : appStateListeners) {
            appStateListener.valueChanged();
        }
    }
}
