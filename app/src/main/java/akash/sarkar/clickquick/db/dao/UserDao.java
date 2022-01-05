package akash.sarkar.clickquick.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import akash.sarkar.clickquick.db.entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * from user")
    User getUser();

    @Insert
    long registerUser(User user);

    @Query("Delete from user")
    void emptyUser();
}
