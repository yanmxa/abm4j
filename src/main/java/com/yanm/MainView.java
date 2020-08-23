package com.yanm;

import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.model.CellState;
import com.yanm.model.StandardRule;
import com.yanm.viewmodel.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Infobar infobar;

    private Canvas canvas;

    private Affine affine;

    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel,
                    EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {

        this.boardViewModel = boardViewModel;
        this.editorViewModel = editorViewModel;

        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);

        this.infobar = new Infobar(this.editorViewModel);
        this.infobar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, this.infobar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

//        draw();
//        this.boardViewModel.setBoard(initialBoard);
    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            editorViewModel.setDrawMode(CellState.ALIVE);
        } else if (keyEvent.getCode() == KeyCode.E) {
            editorViewModel.setDrawMode(CellState.DEAD);
        }

    }

    private void handleDraw(MouseEvent event) {

        Point2D simCoord = getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.editorViewModel.boardPressed(simX, simY);
    }

    private void handleMoved(MouseEvent event) {
        Point2D simCoord = getSimulationCoordinates(event);
        this.infobar.setCursorPosition((int)simCoord.getX(), (int)simCoord.getY());
    }

    private Point2D getSimulationCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return simCoord;
        } catch (NonInvertibleTransformException e) {
            throw new IllegalArgumentException("Non invertible transform");
        }
    }


    public void draw(Board board) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 10, 10);
        
        drawSimulation(board);

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }
        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0, y, 10, y);
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
