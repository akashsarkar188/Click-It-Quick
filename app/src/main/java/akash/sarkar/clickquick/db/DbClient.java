package akash.sarkar.clickquick.db;

import android.content.Context;

import androidx.room.Room;

public class DbClient {
    private static DbClient instance = null;
    private final RoomDb roomDb;

    private DbClient(Context context) {
        roomDb = Room.databaseBuilder(context, RoomDb.class, "ClickItQuick").build();
    }

    public synchronized static DbClient getInstance(Context context) {
        if (instance == null) {
            instance = new DbClient(context);
        }
        return instance;
    }

    public RoomDb getDatabase() {
        return roomDb;
    }
}
