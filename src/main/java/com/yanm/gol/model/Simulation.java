package com.yanm.gol.model;


public class Simulation {

    private Board simulationBoard;
    private SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {

        Board nextBoard = simulationBoard.copy();
        for (int y = 0; y < simulationBoard.getHeight(); y++) {
            for (int x = 0; x < simulationBoard.getWidth(); x++) {
                CellState nextState = simulationRule.getNextState(x, y, simulationBoard);
                nextBoard.setState(x, y, nextState);
            }
        }
        this.simulationBoard = nextBoard;
    }

    public Board getBoard() {
        return simulationBoard;
    }
}
