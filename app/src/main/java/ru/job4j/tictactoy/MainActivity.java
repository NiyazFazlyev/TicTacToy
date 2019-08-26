package ru.job4j.tictactoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String[] MARK = {"O", "X"};
    private int count = 0;
    private final List<Button> cells = new ArrayList<>();
    private List<String> marks = new ArrayList<>();
    private Logic logic;
    private Switch auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setButtons();
        if (savedInstanceState != null){
            this.marks = savedInstanceState.getStringArrayList("marks");
        } else {
            this.initMarks();
        }
        logic = new Logic(marks, MARK);
        auto = findViewById(R.id.switch1);
//        this.play();
    }

    private void setButtons() {
        this.cells.add((Button) findViewById(R.id.cell11));
        this.cells.add((Button) findViewById(R.id.cell12));
        this.cells.add((Button) findViewById(R.id.cell13));
        this.cells.add((Button) findViewById(R.id.cell21));
        this.cells.add((Button) findViewById(R.id.cell22));
        this.cells.add((Button) findViewById(R.id.cell23));
        this.cells.add((Button) findViewById(R.id.cell31));
        this.cells.add((Button) findViewById(R.id.cell32));
        this.cells.add((Button) findViewById(R.id.cell33));
    }

    private void initMarks() {
        for (int index = 0; index != cells.size(); index++) {
            marks.add("");
        }
    }

    public void clickCell(View view) {
        Button btn = (Button) view;
        setMark(btn);
        boolean finish = this.check();

        if (!finish && !auto.isChecked()) {
            btn = cells.get(logic.getMove());
            setMark(btn);
            this.check();
        }
    }

    private void  setMark(Button button){
        button.setText(MARK[count % 2]);
        marks.set(cells.indexOf(button), MARK[count % 2]);
        count++;
        button.setEnabled(false);
    }

    private boolean check() {
        boolean finish = false;
        if (logic.isWinnerO()) {
            Toast.makeText(this, "Win O",
                    Toast.LENGTH_LONG
            ).show();
            this.clearTable();
            finish = true;
        }
        if (logic.isWinnerX()) {
            Toast.makeText(this, "Win X",
                    Toast.LENGTH_LONG
            ).show();
            this.clearTable();
            finish = true;
        }
        if (!logic.hasGap()) {
            Toast.makeText(this, "No free cells",
                    Toast.LENGTH_LONG
            ).show();
            this.clearTable();
            finish = true;
        }
        return finish;
    }

    private void clearTable() {
        for (int index = 0; index != cells.size(); index++) {
            Button button = this.cells.get(index);
            button.setEnabled(true);
            button.setText("");
            this.marks.set(index, "");
        }
        this.count = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
        outState.putBoolean("switch", auto.isChecked());
        boolean[] enables = new boolean[this.cells.size()];
        for (int index = 0; index != cells.size(); index++) {
            enables[index] = cells.get(index).isEnabled();
        }
        outState.putStringArrayList("marks", (ArrayList<String>) this.marks);
        outState.putBooleanArray("enables", enables);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        this.count = saveInstanceState.getInt("count");
        boolean[] enables = saveInstanceState.getBooleanArray("enables");
        for (int index = 0; index != cells.size(); index++) {
            this.cells.get(index).setEnabled(enables[index]);
            this.cells.get(index).setText(marks.get(index));
        }
        auto.setChecked(saveInstanceState.getBoolean("switch"));
    }

}