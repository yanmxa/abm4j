package com.yanm;

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

    private Simulation initialSimulation;
    private Simulation simulation;



    private Simulator simulator;

    private int drawMode = Simulation.ALIVE;

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

        this.initialSimulation = new Simulation(10, 10);
        this.simulation = Simulation.copy(this.initialSimulation);

        draw();
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            drawMode = Simulation.ALIVE;
            System.out.println("Draw mode");
        } else if (keyEvent.getCode() == KeyCode.E) {
            drawMode = Simulation.DEAD;
            System.out.println("Erase mode");
        }

    }

    private void handleDraw(MouseEvent event) {

        if (applicationState == SIMULATING) return;

        Point2D simCoord = getSimulationCoordinates(event);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        initialSimulation .setState(simX, simY, drawMode);
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
        
        if (this.applicationState == EDITING) drawSimulation(this.initialSimulation);
        else drawSimulation(this.simulation);

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= simulation.width; x++) {
            g.strokeLine(x, 0, x, 10);
        }
        for (int y = 0; y <= simulation.height; y++) {
            g.strokeLine(0, y, 10, y);
        }
    }

    public void drawSimulation(Simulation simulation) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulation.width; x++) {
            for (int y = 0; y < simulation.height; y++) {
                if (simulation.getState(x, y) == Simulation.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }

            }
        }
    }

    public Simulation getSimulations() {
        return simulation;
    }

    public void setDrawMode(int i) {
        this.drawMode = i;
        this.infobar.setDrawMode(drawMode);
    }

    public void setApplicationState(int applicationState) {
        if (this.applicationState == applicationState) return;

        if (applicationState == SIMULATING) {
            this.simulation = Simulation.copy(this.initialSimulation);
            this.simulator = new Simulator(this.simulation, this);
        }

        this.applicationState = applicationState;
        System.out.println("Application State: " + applicationState);
    }


    public Simulator getSimulator() {
        return simulator;
    }
}
