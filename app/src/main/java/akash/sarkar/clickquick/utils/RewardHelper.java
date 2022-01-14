package akash.sarkar.clickquick.utils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import akash.sarkar.clickquick.db.DbHelper;
import akash.sarkar.clickquick.db.entities.Levels;
import akash.sarkar.clickquick.db.listeners.LevelsListener;
import akash.sarkar.clickquick.db.listeners.RewardsListener;
import akash.sarkar.clickquick.ui.fragments.RewardGivingFragment;

public class RewardHelper {

    public static void addReward(Context context, int forLevel, FragmentManager fragmentManager, RewardsListener.onAddRewardListener listener) {
        DbHelper.getInstance(context).getLevel(forLevel, new LevelsListener.onLevelGetListener() {
            @Override
            public void onSuccess(Levels level) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(android.R.id.content, RewardGivingFragment.newInstance(level.winingReward, forLevel, fragmentManager, listener));
                fragmentTransaction.addToBackStack("Frag");
                fragmentTransaction.commit();
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure("No such level exists");
            }
        });
    }
}
