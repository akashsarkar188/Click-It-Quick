package akash.sarkar.clickquick.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import akash.sarkar.clickquick.db.dao.RewardsDao;
import akash.sarkar.clickquick.db.dao.UserDao;
import akash.sarkar.clickquick.db.entities.Rewards;
import akash.sarkar.clickquick.db.entities.User;

@Database(entities = {User.class, Rewards.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract RewardsDao rewardsDao();
}
