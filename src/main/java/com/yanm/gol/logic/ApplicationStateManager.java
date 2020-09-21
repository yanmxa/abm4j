package com.yanm.gol.logic;

import com.yanm.app.observable.Property;

public class ApplicationStateManager {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
