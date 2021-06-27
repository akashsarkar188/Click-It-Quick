package akash.sarkar.clickquick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private ColorsAdapter adapter;
    private List<ColorsModel> list = new ArrayList<>();
    private RecyclerView colorsRecyclerView;
    private MaterialButton startStopGame;
    private Handler gameTimer;
    private Runnable runnable;
    private boolean gameIsActive = false;
    private TextView scoreTextView;
    private int currentScore = 0;
    private long gameDelay = 1000;
    private int[] colorIds = new int[]{R.color.red, R.color.green, R.color.yellow, R.color.blue, R.color.grey};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initView();
        //startGame();
    }

    private void startGame() {

        currentScore = 0;
        scoreTextView.setText(String.valueOf(currentScore));

        if (gameTimer == null) {
            gameTimer = new Handler(Looper.myLooper());
        }

        gameTimer.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                setUpColorListAndDisplay();
                gameTimer.postDelayed(runnable, gameDelay);
            }
        },  gameDelay);
    }

    private void setUpColorListAndDisplay() {
        List<ColorsModel> updatedList = new ArrayList<>();

        ArrayList<Integer> alreadyAddedPosition = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(1, 6);
            if (randomInt > 0 && randomInt < 6) {
                if (!alreadyAddedPosition.contains(randomInt)) {
                    alreadyAddedPosition.add(randomInt);
                    updatedList.add(getColorModel(randomInt));
                } else {


                    for (int j = 0; j < 4; j++) {
                        int randomInt2 = ThreadLocalRandom.current().nextInt(1, 6);
                        if (randomInt2 > 0 && randomInt2 < 6) {
                            if (!alreadyAddedPosition.contains(randomInt2)) {
                                alreadyAddedPosition.add(randomInt2);
                                updatedList.add(getColorModel(randomInt2));
                                break;
                            }
                        }
                    }


                }
            }
        }

        Log.e("XXX", "setUpColorListAndDisplay: "  + updatedList.size());
        Log.e("XXX", "setUpColorListAndDisplay: "  + alreadyAddedPosition);
        if (startStopGame != null) {
            startStopGame.setText("Stop Game");
            gameIsActive = true;
        }
        adapter.addData(updatedList);
    }

    private ColorsModel getColorModel(int position) {
        switch (position) {
            case 1:
                return new ColorsModel(colorIds[0], false);
            case 2:
                return new ColorsModel(colorIds[1], false);
            case 3:
                return new ColorsModel(colorIds[2], false);
            case 4:
                return new ColorsModel(colorIds[3], false);
            default:
                return new ColorsModel(colorIds[4], true);
        }
    }

    private void initView() {

        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        scoreTextView = findViewById(R.id.scoreTextView);
        startStopGame = findViewById(R.id.startStopGame);

        colorsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter = new ColorsAdapter(this, list, new ColorsAdapter.colorBlockClickListener() {
            @Override
            public void OnColorClicked(ColorsModel data) {
                if (data.isGrey()) {
                    currentScore++;
                    scoreTextView.setText(String.valueOf(currentScore));
                } else {
                    handleGameOver();
                }
            }
        });
        colorsRecyclerView.setAdapter(adapter);

        startStopGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameIsActive) {
                    handleGameOver();
                } else {
                    startGame();
                }
            }
        });
    }

    private void handleGameOver() {


        gameIsActive = false;
        startStopGame.setText("Start Game");
        gameTimer.removeCallbacks(runnable);

        showGameOverDialog();
    }

    private void showGameOverDialog() {

        new AlertDialog.Builder(this).
                setTitle("Game Over")
                .setMessage(String.format("Your score is : %s", String.valueOf(currentScore)))
                .setPositiveButton("Lets try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentScore = 0;
                        dialog.dismiss();
                        startGame();
                    }
                })
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentScore = 0;
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();


    }
}