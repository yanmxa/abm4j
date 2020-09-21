package com.yanm.gol.view;

import javafx.scene.canvas.GraphicsContext;

public interface DrawLayer {

    void draw(GraphicsContext g);

    int getLayer();

    void addInvalidationListener(InvalidationListener invalidationListener);
}
