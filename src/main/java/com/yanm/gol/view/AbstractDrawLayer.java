package com.yanm.gol.view;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDrawLayer implements DrawLayer {

    private List<InvalidationListener> listeners = new LinkedList<>();

    @Override
    public void addInvalidationListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    protected void invalidate() {
        listeners.forEach(InvalidationListener::onInvalidated);
    }
}
