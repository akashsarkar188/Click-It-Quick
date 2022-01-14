package akash.sarkar.clickquick.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import akash.sarkar.clickquick.ColorsAdapter;
import akash.sarkar.clickquick.ColorsModel;
import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.db.DbHelper;
import akash.sarkar.clickquick.db.entities.User;
import akash.sarkar.clickquick.db.listeners.RewardsListener;
import akash.sarkar.clickquick.db.listeners.UserListener;
import akash.sarkar.clickquick.utils.RewardHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ColorsAdapter adapter;
    private List<ColorsModel> list = new ArrayList<>();
    private RecyclerView colorsRecyclerView;
    private MaterialButton startStopGame;
    private Handler gameTimer;
    private Runnable runnable;
    private boolean gameIsActive = false, didUserTap = false;
    private TextView scoreTextView, rewPoints, userNameTV, userInitials;
    private int currentScore = 0;
    private long gameDelay = 1000;
    private Integer[] colorIds = new Integer[]{R.color.red, R.color.blue, R.color.yellow, R.color.green, R.color.grey};
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper.getInstance(this).isLoggedIn(isLoggedIn -> {
            Log.e(TAG, "onCreate: " + isLoggedIn);
            if (!isLoggedIn) {
                Toast.makeText(MainActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AuthActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finishAffinity();
            } else {
                init();
            }
        });
        //startGame();
    }

    private void init() {
        initElements();
        checkIfFirstTime();
    }

    private void checkIfFirstTime() {
        DbHelper.getInstance(this).getRewardsTotal(totalRewards -> {
            if (totalRewards > 0) {
                rewPoints.setText(String.valueOf(totalRewards));
            } else {
                RewardHelper.addReward(this, 1, getSupportFragmentManager(), new RewardsListener.onAddRewardListener() {
                    @Override
                    public void onSuccess() {
                        checkIfFirstTime();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initElements() {

        userNameTV = findViewById(R.id.userNameTV);
        userInitials = findViewById(R.id.userInitials);
        DbHelper.getInstance(this).getUser(user -> {
            MainActivity.this.user = user;
            runOnUiThread(() -> {
                userNameTV.setText(user.userName);
                userInitials.setText(user.userName.substring(0, 1).toUpperCase(Locale.ROOT));
            });
        });
        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        rewPoints = findViewById(R.id.rewPoints);
        scoreTextView = findViewById(R.id.scoreTextView);
        startStopGame = findViewById(R.id.startStopGame);

        colorsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter = new ColorsAdapter(this, list, new ColorsAdapter.colorBlockClickListener() {
            @Override
            public void OnColorClicked(ColorsModel data) {
                if (data.isGrey()) {
                    didUserTap = true;
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


    private void startGame() {

        currentScore = 0;
        didUserTap = true;
        scoreTextView.setText(String.valueOf(currentScore));

        if (gameTimer == null) {
            gameTimer = new Handler(Looper.myLooper());
        }

        gameTimer.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                if (!didUserTap) {
                    handleGameOver();
                } else {
                    didUserTap = false;
                    setUpColorListAndDisplay();
                    gameTimer.postDelayed(runnable, gameDelay);
                }
            }
        }, gameDelay);
    }

    private void setUpColorListAndDisplay() {
        List<ColorsModel> updatedList = new ArrayList<>();

        List<Integer> colorIds2 = new ArrayList<>();
        colorIds2.add(R.color.red);
        colorIds2.add(R.color.green);
        colorIds2.add(R.color.yellow);
        colorIds2.add(R.color.blue);

        int i = 0;
        int boundValue = 4;

        /*while (true) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, boundValue);

            updatedList.add(new ColorsModel(colorIds2.get(randomInt), false));
            colorIds2.remove(colorIds2.get(randomInt));
            boundValue--;

            i++;
            if (i == 4) {
                break;
            }
        }*/

        updatedList.add(getColorModel(1));
        updatedList.add(getColorModel(2));
        updatedList.add(getColorModel(3));
        updatedList.add(getColorModel(4));

        int randomInt = ThreadLocalRandom.current().nextInt(0, 4);
        updatedList.set(randomInt, getColorModel(5));

        /*if (updatedList.size() > 4) {
            for (int o = 4; o < updatedList.size(); o++) {
                updatedList.remove(o);
            }
        }*/

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