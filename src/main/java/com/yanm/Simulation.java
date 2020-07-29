package com.yanm;

public class Simulation {

    public static int DEAD  = 0;
    public static int ALIVE = 1;

    int width;
    int height;
    int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public static Simulation copy(Simulation simulation) {
        Simulation copy = new Simulation(simulation.width, simulation.height);
        for (int x = 0; x < copy.width; x++) {
            for (int y = 0; y < copy.height; y++) {
                copy.setState(x, y, simulation.getState(x, y));
            }
        }
        return copy;
    }

    public void step() {

        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbors = countAliveNeighbours(x, y);
                if (board[x][y] == ALIVE) {
                    if (aliveNeighbors < 2) {
                        newBoard[x][y] = DEAD;
                    } else if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                        newBoard[x][y] = ALIVE;
                    } else if (aliveNeighbors > 3) {
                        newBoard[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbors == 3) newBoard[x][y] = ALIVE;
                }
            }
        }

        this.board = newBoard;

    }

    public void setAlive(int x, int y) {
        setState(x, y, ALIVE);
    }

    public void setDead(int x, int y) {
        setState(x, y, DEAD);
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        count += isAlive(x - 1, y - 1);
        count += isAlive(x - 1, y );
        count += isAlive(x - 1, y + 1);

        count += isAlive(x , y - 1);
        count += isAlive(x , y + 1);

        count += isAlive(x + 1, y - 1);
        count += isAlive(x + 1, y);
        count += isAlive(x + 1, y + 1);

        return count;
    }

    public int isAlive(int x, int y) {
        if (x < 0 || x >= width) return 0;
        if (y < 0 || y >= height) return 0;
        return board[x][y];
    }

    public void setState(int x, int y, int state) {
        if (x < 0 || x >= width) return;
        if (y < 0 || y >= height) return;
        board[x][y] = state;
    }

    public int getState(int x, int y) {
        return board[x][y];
    }

    public void printBoard() {
        System.out.println("----");
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (board[x][y] == DEAD) {
                    line += ".";
                } else {
                    line += "*";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("----\n");
    }

}
