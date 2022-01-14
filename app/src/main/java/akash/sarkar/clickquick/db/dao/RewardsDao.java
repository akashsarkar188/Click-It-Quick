package akash.sarkar.clickquick.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import akash.sarkar.clickquick.db.entities.Rewards;

@Dao
public interface RewardsDao {

    @Query("SELECT SUM(rewards) as totalRewards from rewards where user_id=:userId")
    long getTotalRewards(long userId);

    @Insert
    long addReward(Rewards reward);
}
