package com.yanm;

import com.yanm.model.Board;
import com.yanm.model.BoundedBoard;
import com.yanm.model.CellState;
import com.yanm.model.StandardRule;
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

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private Infobar infobar;

    private Canvas canvas;

    private Affine affine;

    private Board initialBoard;
    private Simulation simulation;

//    private Simulator simulator;

    private CellState drawMode = CellState.ALIVE;

    public int getApplicationState() {
        return applicationState;
    }

    private int applicationState = EDITING;

    public MainView() {

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.infobar = new Infobar();
        this.infobar.setDrawMode(this.drawMode);
        this.infobar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, this.infobar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.initialBoard = new BoundedBoard(10, 10);

        draw();
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            drawMode = CellState.ALIVE;
            System.out.println("Draw mode");
        } else if (keyEvent.getCode() == KeyCode.E) {
            drawMode = CellState.DEAD;
            System.out.println("Erase mode");
        }

    }

    private void handleDraw(MouseEvent event) {

        if (applicationState == SIMULATING) return;

        Point2D simCoord = getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        initialBoard.setState(simX, simY, drawMode);
        draw();
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


    public void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 10, 10);
        
        if (this.applicationState == EDITING) drawSimulation(this.initialBoard);
        else drawSimulation(this.simulation.getBoard());

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }
        for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
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

    public Simulation getSimulation() {
        return simulation;
    }

    public void setDrawMode(CellState i) {
        this.drawMode = i;
        this.infobar.setDrawMode(drawMode);
    }

    public void setApplicationState(int applicationState) {
        if (this.applicationState == applicationState) return;

        if (applicationState == SIMULATING) {
            this.simulation = new Simulation(this.initialBoard, new StandardRule());
        }

        this.applicationState = applicationState;
        System.out.println("Application State: " + applicationState);
    }

}
