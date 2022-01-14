package akash.sarkar.clickquick.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import akash.sarkar.clickquick.db.entities.Levels;

@Dao
public interface LevelsDao {

    @Query("SELECT * FROM levels")
    List<Levels> getAllLevels();

    @Query("SELECT * FROM levels where level=:level")
    Levels getLevel(int level);

    @Insert
    void addLevels(Levels... levels);
}
