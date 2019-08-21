package ru.job4j.tictactoy;

import android.os.Build;
import android.widget.Button;

import java.util.List;


public class Logic {
    private final List<Button> table;
    private final String[] mark;
    private final int size;


    public Logic(List<Button> buttons, String[] mark) {
        this.table = buttons;
        this.size = (int) Math.sqrt(buttons.size());
        this.mark = mark;
    }


    public boolean hasGap() {
        boolean result = false;
        for (Button button : table) {
            if (!button.getText().toString().equals(mark[0]) && !button.getText().toString().equals(mark[1])) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean fillBy(String mark, int startX, int startY, int deltaX, int deltaY) {
        boolean result = true;
        for (int index = 0; index != this.size; index++) {
            Button button = this.table.get(startX + this.size * startY);
            if (!button.getText().toString().equals(mark)) {
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