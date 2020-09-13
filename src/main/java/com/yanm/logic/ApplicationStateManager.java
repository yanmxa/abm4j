package com.yanm.logic;

import com.yanm.common.Property;

public class ApplicationStateManager {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
