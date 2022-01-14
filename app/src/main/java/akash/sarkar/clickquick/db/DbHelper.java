package akash.sarkar.clickquick.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import akash.sarkar.clickquick.db.entities.Levels;
import akash.sarkar.clickquick.db.entities.Rewards;
import akash.sarkar.clickquick.db.entities.User;
import akash.sarkar.clickquick.db.listeners.LevelsListener;
import akash.sarkar.clickquick.db.listeners.RewardsListener;
import akash.sarkar.clickquick.db.listeners.UserListener;
import akash.sarkar.clickquick.ui.activities.MainActivity;

public class DbHelper {
    private Context context;
    private Executor executor;
    private RoomDb roomDb;

    private DbHelper(Context context) {
        this.context = context;
        executor = Executors.newSingleThreadExecutor();
        roomDb = DbClient.getInstance(context).getDatabase();

    }

    public static DbHelper getInstance(Context context) {
        return new DbHelper(context);
    }


    /* ------ USER ----------*/

    public void isLoggedIn(UserListener.onLoginCheckListener listener) {
        executor.execute(() -> {
            User user = roomDb.userDao().getUser();
            ((Activity) context).runOnUiThread(() -> listener.loginCheck(user != null));
        });
    }

    public void registerUser(String username, UserListener.onRegisterUserListener listener) {
        executor.execute(() -> {
            if (roomDb.userDao().registerUser(new User(username, 0, System.currentTimeMillis())) > 0) {
                ((Activity) context).runOnUiThread(listener::onSuccess);
            } else {
                ((Activity) context).runOnUiThread(() -> listener.onFailure("Something's went wrong"));
            }
        });
    }

    public void getUser(UserListener.onGetUserListener listener) {
        executor.execute(() -> {
            User user = roomDb.userDao().getUser();
            listener.onSuccess(user);
        });
    }


    /* ------ REWARDS ----------*/

    public void getRewardsTotal(RewardsListener.onGetTotalRewardsListener listener) {
        getUser(user -> {
            long totalRewards = roomDb.rewardsDao().getTotalRewards(user.userId);
            ((Activity) context).runOnUiThread(() -> listener.onSuccess(totalRewards));
        });
    }

    public void addRewardForLevel(int level, RewardsListener.onAddRewardListener listener) {
        getUser(user -> getLevel(level, new LevelsListener.onLevelGetListener() {
            @Override
            public void onSuccess(Levels levels) {
                executor.execute(() -> {
                    roomDb.rewardsDao().addReward(new Rewards(user.userId, levels.winingReward, level));
                    getRewardsTotal(totalRewards -> listener.onSuccess());
                });
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        }));
    }


    /* ------ LEVELS ----------*/


    public void addLevels(LevelsListener.onLevelAddListener listener, Levels... levels) {
        executor.execute(() -> {
            roomDb.levelsDao().addLevels(levels);
            listener.onSuccess();

        });
    }

    public void getLevel(int level, LevelsListener.onLevelGetListener listener) {
        executor.execute(() -> {
            Levels mLevel = roomDb.levelsDao().getLevel(level);
            ((Activity) context).runOnUiThread(() -> {
                if (mLevel == null) {
                    listener.onFailure("No such level exist");
                } else {
                    listener.onSuccess(mLevel);
                }
            });
        });
    }

    public void getAllLevels(LevelsListener.onGetAllLevelsListener listener) {
        executor.execute(() -> {
            List<Levels> levels = roomDb.levelsDao().getAllLevels();
            ((Activity) context).runOnUiThread(() -> {
                if (levels.size() < 1) {
                    listener.onFailure("No levels exist");
                } else {
                    listener.onSuccess(levels);
                }
            });
        });
    }
}
