package com.yanm.gol.view;

import com.yanm.app.event.EventBus;
import com.yanm.gol.components.editor.BoardEvent;
import com.yanm.gol.model.Board;
import com.yanm.gol.model.CellPosition;
import com.yanm.gol.model.CellState;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimulationCanvas extends Pane {

    private Canvas canvas;

    private Affine affine;

    private EventBus eventBus;

    private List<DrawLayer> drawLayers = new LinkedList<>();

    public SimulationCanvas(EventBus eventBus) {
        this.eventBus = eventBus;

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

    public void addDrawLayer(DrawLayer drawLayer) {
        drawLayers.add(drawLayer);
        drawLayers.sort(Comparator.comparingInt(DrawLayer::getLayer));
        drawLayer.addInvalidationListener(this::draw);
    }



    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw();
    }

    private void handleDraw(MouseEvent event) {
        CellPosition cursorPosition = getSimulationCoordinates(event);
        BoardEvent boardEvent = new BoardEvent(BoardEvent.Type.CURSOR_PRESSED, cursorPosition);
        eventBus.emit(boardEvent);
    }

    private void handleCursorMoved(MouseEvent mouseEvent) {
        CellPosition cursorPosition = this.getSimulationCoordinates(mouseEvent);
        BoardEvent boardEvent = new BoardEvent(BoardEvent.Type.CURSOR_MOVED, cursorPosition);
        eventBus.emit(boardEvent);
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


    private void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 450, 450);

        for (DrawLayer drawLayer : drawLayers) {
            drawLayer.draw(g);
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
