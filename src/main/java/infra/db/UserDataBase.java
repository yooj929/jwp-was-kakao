package infra.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import user.User;

public class UserDataBase {

    private static final Map<String, User> users = new HashMap<>();

    private UserDataBase(){}

    public static User insert(User user){
        users.put(user.getUserId(), user);
        return users.get(user.getUserId());
    }
    public static User findByUserId(String userId){
        return users.get(userId);
    }
    public static Collection<User> findAll(){
        return users.values();
    }

    public static void deleteAll(){
        users.clear();
    }

}
