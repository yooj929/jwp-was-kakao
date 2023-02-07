package auth;

import user.User;

public class AuthLoginUserDetails {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public AuthLoginUserDetails(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static AuthLoginUserDetails of(User user){
        return new AuthLoginUserDetails(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
