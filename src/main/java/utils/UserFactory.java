package utils;

import model.User;

import java.util.Map;

public class UserFactory {

    private UserFactory(){

    }

    public static User createUser(Map<String, String> userData){
        return new User(userData.get("userId"),
                userData.get("password"),
                userData.get("name"),
                userData.get("email"));
    }
}
