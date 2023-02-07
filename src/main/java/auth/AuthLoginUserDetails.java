package auth;

import businuess.user.User;

public class AuthLoginUserDetails {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    private String uuid;

    public AuthLoginUserDetails(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static AuthLoginUserDetails of(User user){
        return new AuthLoginUserDetails(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
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

    public String getUuid() {
        return uuid;
    }

    public User toUser(){
        return User.builder()
                .name(name)
                .userId(userId)
                .password(password)
                .email(email)
                .build();
    }

    @Override
    public String toString() {
        return "AuthLoginUserDetails{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
