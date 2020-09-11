package com.yanm.view;

import com.yanm.model.Board;
import com.yanm.model.CellPosition;
import com.yanm.model.CellState;
import com.yanm.viewmodel.BoardViewModel;
import com.yanm.viewmodel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    private Canvas canvas;
    private Affine affine;
    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;


    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        boardViewModel.getBoard().listen(this::draw);
        editorViewModel.getCursorPosition().listen(cellPosition -> draw(boardViewModel.getBoard().get()));

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleCursorMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(canvas);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

    }



    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(boardViewModel.getBoard().get());
    }

    private void handleDraw(MouseEvent event) {
        CellPosition cursorPosition = getSimulationCoordinates(event);
        this.editorViewModel.boardPressed(cursorPosition);
    }

    private void handleCursorMoved(MouseEvent mouseEvent) {
        CellPosition cursorPosition = this.getSimulationCoordinates(mouseEvent);
        this.editorViewModel.getCursorPosition().set(cursorPosition);
    }

    private CellPosition getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return new CellPosition((int) simCoord.getX(), (int) simCoord.getY());
        } catch (NonInvertibleTransformException e) {
            throw new IllegalArgumentException("Non invertible transform");
        }
    }


    private void draw(Board board) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 450, 450);

        drawSimulation(board);

        CellPosition cursor = editorViewModel.getCursorPosition().get();
        if (editorViewModel.getCursorPosition().isPresent()) {
            g.setFill(new Color(0.3, 0.3,0.3, 0.5));
            g.fillRect(cursor.getX(), cursor.getY(), 1, 1);
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, board.getHeight());
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, board.getWidth(), y);
        }
    }

    public void drawSimulation(Board simulation2Draw) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulation2Draw.getWidth(); x++) {
            for (int y = 0; y < simulation2Draw.getHeight(); y++) {
                if (simulation2Draw.getState(x, y) == CellState.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }

            }
        }
    }
}
