package ru.job4j.tictactoy;

import java.util.List;


public class Logic {
    private final List<String> table;
    private final String[] mark;
    private final int size;


    public Logic(List<String> table, String[] mark) {
        this.table = table;
        this.size = (int) Math.sqrt(table.size());
        this.mark = mark;
    }

    public int getMove(){
        while (true) {
            int rnd = (int) (8 * Math.random());
            if (this.table.get(rnd).equals("")) {
                return rnd;
            }
        }
    }

    public boolean hasGap() {
        boolean result = false;
        for (String value : table) {
            if (!value.equals(mark[0]) && !value.equals(mark[1])) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean fillBy(String mark, int startX, int startY, int deltaX, int deltaY) {
        boolean result = true;
        for (int index = 0; index != this.size; index++) {
            String value = this.table.get(startX + this.size * startY);
            if (!value.equals(mark)) {
                result = false;
                break;
            }
            startX += deltaX;
            startY += deltaY;
        }
        return result;

    }

    private boolean isWinner(String mark) {
        return this.fillBy(mark, 0, 0, 1, 0)
                || this.fillBy(mark, 0, 1, 1, 0)
                || this.fillBy(mark, 0, 2, 1, 0)
                || this.fillBy(mark, 0, 0, 0, 1)
                || this.fillBy(mark, 1, 0, 0, 1)
                || this.fillBy(mark, 2, 0, 0, 1)
                || this.fillBy(mark, 0, 0, 1, 1)
                || this.fillBy(mark, this.size - 1, 0, -1, 1);
    }

    public boolean isWinnerX() {
        return this.isWinner(mark[1]);
    }

    public boolean isWinnerO() {
        return this.isWinner(mark[0]);
    }
}