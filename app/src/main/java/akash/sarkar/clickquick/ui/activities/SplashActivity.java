package akash.sarkar.clickquick.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.db.DbClient;
import akash.sarkar.clickquick.db.RoomDb;

public class SplashActivity extends AppCompatActivity {

    private RoomDb roomDb;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        initElements();
        new Handler().postDelayed(this::authentication, 2000);
    }


    private void initElements() {
        roomDb = DbClient.getInstance(this).getDatabase();
        executor = Executors.newSingleThreadExecutor();
    }

    private void authentication() {
        executor.execute(() -> {
            if (roomDb.userDao().getUser() == null) {
                startActivity(new Intent(this, AuthActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        });
    }
}