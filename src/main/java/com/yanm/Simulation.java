package com.yanm;

import com.yanm.model.Board;
import com.yanm.model.CellState;
import com.yanm.model.SimulationRule;

import static com.yanm.model.CellState.ALIVE;

public class Simulation {

    private Board simulationBoard;
    private SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {

        Board nextBoard = simulationBoard.copy();
        for (int y = 0; y < simulationBoard.getWidth(); y++) {
            for (int x = 0; x < simulationBoard.getHeight(); x++) {
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
