package com.ptithcm.thuan6420.basecleanarchitecture.data.repositories;

import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource;
import com.ptithcm.thuan6420.basecleanarchitecture.data.models.User;

public class UserRepository {
    private static UserRepository Instance;

    public static UserRepository getInstance() {
        if (Instance != null) {
            return Instance;
        }
        return new UserRepository();
    }

    public static void createUser(User user) {
        UserLocalDataSource.setUser(user);
    }

    public static boolean isLoginSuccess(User user) {
        User userInLocal = UserLocalDataSource.getUser();
        if (userInLocal == null) {
            return false;
        }
        return user.getEmail().equalsIgnoreCase(userInLocal.getEmail())
                && user.getPassword().equalsIgnoreCase(userInLocal.getPassword());
    }
}
