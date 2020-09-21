package com.yanm.gol.model;

public class BoundedBoard implements Board {

    private int width;
    private int height;
    private CellState[][] board;


    public BoundedBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[width][height];
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.setState(x, y, CellState.DEAD);
            }
        }
    }


    @Override
    public Board copy() {
        BoundedBoard copy = new BoundedBoard(this.width, this.height);
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                copy.setState(x, y, this.getState(x, y));
            }
        }
        return copy;
    }

    @Override
    public CellState getState(int x, int y) {
        if (x < 0 || x >= width) return CellState.DEAD;
        if (y < 0 || y >= height) return CellState.DEAD;
        return board[x][y];
    }

    @Override
    public void setState(int x, int y, CellState cellState) {
        if (x < 0 || x >= width) return;
        if (y < 0 || y >= height) return;
        board[x][y] = cellState;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

}
