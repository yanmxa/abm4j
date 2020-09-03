package com.yanm.view;

import com.yanm.model.Board;
import com.yanm.model.CellState;
import com.yanm.viewmodel.BoardViewModel;
import com.yanm.viewmodel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        boardViewModel.listenToBoard(this::draw);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(canvas);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(boardViewModel.getBoard());
    }

    private void handleDraw(MouseEvent event) {

        Point2D simCoord = getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.editorViewModel.boardPressed(simX, simY);
    }

//    private void handleMoved(MouseEvent event) {
//        Point2D simCoord = getSimulationCoordinates(event);
//        this.infobar.setCursorPosition((int)simCoord.getX(), (int)simCoord.getY());
//    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        try {
            return this.affine.inverseTransform(mouseX, mouseY);
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

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, 14);
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, 14, y);
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
