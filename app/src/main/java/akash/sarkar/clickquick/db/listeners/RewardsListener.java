package akash.sarkar.clickquick.db.listeners;

public interface RewardsListener {
    interface onGetTotalRewardsListener {
        void onSuccess(long totalRewards);
    }

    interface onAddRewardListener {
        void onSuccess();

        void onFailure(String msg);
    }
}
