package akash.sarkar.clickquick.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rewards")
public class Rewards {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int rewardId;

    @ColumnInfo(name = "reward")
    public long reward;

    @ColumnInfo(name = "for_level")
    public int forLevel;

}
