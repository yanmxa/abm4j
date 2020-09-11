package com.yanm.viewmodel;

import com.yanm.common.Property;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
