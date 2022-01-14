package akash.sarkar.clickquick.db.listeners;

import akash.sarkar.clickquick.db.entities.User;

public interface UserListener {
    interface onLoginCheckListener {
        void loginCheck(boolean isLoggedIn);
    }
    interface onGetUserListener{
        void onSuccess(User user);
    }

    interface onRegisterUserListener {
        void onSuccess();

        void onFailure(String msg);
    }
}
