package akash.sarkar.clickquick.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.db.DbClient;
import akash.sarkar.clickquick.db.DbHelper;
import akash.sarkar.clickquick.db.RoomDb;
import akash.sarkar.clickquick.db.entities.Levels;
import akash.sarkar.clickquick.db.listeners.LevelsListener;
import akash.sarkar.clickquick.utils.RewardHelper;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DbHelper.getInstance(this).getAllLevels(new LevelsListener.onGetAllLevelsListener() {
            @Override
            public void onSuccess(List<Levels> levels) {
                for (Levels level : levels) {
                    Log.e(TAG, "onSuccess: " + level.toString());
                }
                new Handler().postDelayed(SplashActivity.this::authentication, 1000);
            }

            @Override
            public void onFailure(String msg) {
                prepareLevels();
            }
        });
    }

    private void prepareLevels() {
        Levels level1 = new Levels(1, 200);
        Levels level2 = new Levels(2, 250);
        Levels level3 = new Levels(3, 370);
        DbHelper.getInstance(this).addLevels(new LevelsListener.onLevelAddListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> new Handler().postDelayed(SplashActivity.this::authentication, 1000));
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, level1, level2, level3);
    }

    private void authentication() {
        DbHelper.getInstance(this).isLoggedIn(isLoggedIn -> {
            if (isLoggedIn) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, AuthActivity.class));
            }
            finish();
        });
    }
}