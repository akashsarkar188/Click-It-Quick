package akash.sarkar.clickquick.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import akash.sarkar.clickquick.R;
import akash.sarkar.clickquick.db.DbHelper;
import akash.sarkar.clickquick.db.entities.Levels;
import akash.sarkar.clickquick.db.listeners.LevelsListener;
import akash.sarkar.clickquick.db.listeners.RewardsListener;
import akash.sarkar.clickquick.utils.DisplayMetricsHelper;

public class RewardGivingFragment extends Fragment {

    private static final String REW_POINTS = "param1";
    private static final String FOR_LEVEL = "param2";

    private View mainView;
    private final FragmentManager fragmentManager;
    private int rewPoints, forLevel;
    private Context context;
    private TextView rewPointsTV, forLevelTV;
    private LottieAnimationView lottieCoin;
    private LinearLayout bgLay;
    private RewardsListener.onAddRewardListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public RewardGivingFragment(FragmentManager fragmentManager, RewardsListener.onAddRewardListener listener) {
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    public static RewardGivingFragment newInstance(int rewPoints,int forLevel, FragmentManager fragmentManager, RewardsListener.onAddRewardListener listener) {
        RewardGivingFragment fragment = new RewardGivingFragment(fragmentManager, listener);
        Bundle args = new Bundle();
        args.putInt(REW_POINTS, rewPoints);
        args.putInt(FOR_LEVEL, forLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rewPoints = getArguments().getInt(REW_POINTS);
            forLevel = getArguments().getInt(FOR_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_rewar_giving, container, false);
        init();
        return mainView;
    }

    private void init() {
        initElements();
        setValues();
        new Handler().postDelayed(this::startAnimation, 2000);
    }


    private void initElements() {
        rewPointsTV = mainView.findViewById(R.id.rewPoints);
        forLevelTV = mainView.findViewById(R.id.lvlName);
        lottieCoin = mainView.findViewById(R.id.lottieCoin);
        bgLay = mainView.findViewById(R.id.bgLay);
    }

    private void setValues() {
        rewPointsTV.setText(String.valueOf(rewPoints));
        forLevelTV.setText("Level : " + forLevel);
    }

    private void addPoints() {
        DbHelper.getInstance(context).addRewardForLevel(forLevel, listener);
    }

    private void startAnimation() {
        int posY = DisplayMetricsHelper.getWindowHeight(context) / 2;
        int posX = DisplayMetricsHelper.getWindowWidth(context) / 2;
        lottieCoin.animate().translationX(posX - 50).setDuration(1800).withStartAction(() -> {
            lottieCoin.animate().scaleX(0).setDuration(1000);
            lottieCoin.animate().scaleY(0).setDuration(1000);
            lottieCoin.animate().translationY(-(posY - 200)).setDuration(1200).withEndAction(
                    () -> bgLay.animate().alpha(0).setDuration(500)
                            .withEndAction(() -> {
                                addPoints();
                                fragmentManager.popBackStack();
                            }));
        });
    }

}