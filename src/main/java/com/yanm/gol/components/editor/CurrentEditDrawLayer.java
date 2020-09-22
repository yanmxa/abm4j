package com.yanm.gol.components.editor;

import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellState;
import com.yanm.gol.view.AbstractDrawLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurrentEditDrawLayer extends AbstractDrawLayer {

    private EditorState editorState;

    public CurrentEditDrawLayer(EditorState editorState) {
        this.editorState = editorState;
        editorState.getCurrentEdit().listen(e -> this.invalidate());
    }

    @Override
    public void draw(GraphicsContext g) {
        if (!editorState.getCurrentEdit().isPresent()) return;
        Edits edits = editorState.getCurrentEdit().get();

        for (Change edit : edits) {
            if (edit.getNewState() == CellState.ALIVE) {
                g.setFill(Color.BLACK);
            } else {
                g.setFill(Color.LIGHTGRAY);
            }
            g.fillRect(edit.getPostition().getX(), edit.getPostition().getY(), 1,1);
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
