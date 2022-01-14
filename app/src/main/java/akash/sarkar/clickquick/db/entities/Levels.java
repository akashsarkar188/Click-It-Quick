package akash.sarkar.clickquick.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "levels")
public class Levels {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int level_id;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "req_points")
    public int requiredPoints;

    @ColumnInfo(name = "wining_reward")
    public int winingReward;

    public Levels(int level, int requiredPoints) {
        this.level = level;
        this.requiredPoints = requiredPoints;
        this.winingReward = (requiredPoints * 35) / 100;
    }

    @Override
    public String toString() {
        return "Levels{" +
                "level_id=" + level_id +
                ", level=" + level +
                ", requiredPoints=" + requiredPoints +
                ", winingReward=" + winingReward +
                '}';
    }
}
