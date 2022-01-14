package akash.sarkar.clickquick.db.listeners;

import java.util.List;

import akash.sarkar.clickquick.db.entities.Levels;

public interface LevelsListener {
    interface onLevelGetListener {
        void onSuccess(Levels level);

        void onFailure(String msg);
    }

    interface onGetAllLevelsListener {
        void onSuccess(List<Levels> levels);

        void onFailure(String msg);
    }

    interface onLevelAddListener {
        void onSuccess();

        void onFailure(String msg);
    }
}
