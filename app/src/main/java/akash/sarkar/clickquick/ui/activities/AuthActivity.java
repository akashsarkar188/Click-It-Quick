package akash.sarkar.clickquick.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.databinding.ActivityAuthBinding;
import akash.sarkar.clickquick.db.DbClient;
import akash.sarkar.clickquick.db.dao.UserDao;
import akash.sarkar.clickquick.db.entities.User;

public class AuthActivity extends AppCompatActivity {

    private EditText usernameEt;
    private Button continueBtn;
    private String username;
    private ActivityAuthBinding binding;
    private Executor executor;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        init();
    }

    private void init() {
        initElements();
        initListeners();
    }


    private void initElements() {
        usernameEt = binding.usernameEt;
        continueBtn = binding.continueBtn;
        executor = Executors.newSingleThreadExecutor();
        userDao = DbClient.getInstance(this).getDatabase().userDao();
        executor.execute(() -> {
            if (userDao.getUser() != null) {
                userDao.emptyUser();
            }
        });
    }

    private void initListeners() {
        continueBtn.setOnClickListener(v -> {
            username = usernameEt.getText().toString();
            if (TextUtils.isEmpty(username)) {
                usernameEt.setError("Username Require");
                return;
            }
            if (username.length() < 4) {
                usernameEt.setError("Length must be more than 4");
                return;
            }
            RegisterUser();
        });
    }

    private void RegisterUser() {
        executor.execute(() -> {
            if (userDao.registerUser(new User(username, 0)) > 0) {
                startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finishAffinity();
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Something's went wrong.", Toast.LENGTH_SHORT).show());
            }
        });
    }
}