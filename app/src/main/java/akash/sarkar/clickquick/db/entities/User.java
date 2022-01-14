package akash.sarkar.clickquick.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public long userId;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "user_avatar")
    public int userAvatar;

    public User(String userName, int userAvatar, long userId) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userId = userId;
    }
}
