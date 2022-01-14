package akash.sarkar.clickquick.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rewards")
public class Rewards {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int rewardId;

    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "rewards")
    public long rewards;

    @ColumnInfo(name = "for_level")
    public int forLevel;

    public Rewards(long userId, long rewards, int forLevel) {
        this.userId = userId;
        this.rewards = rewards;
        this.forLevel = forLevel;
    }
}


