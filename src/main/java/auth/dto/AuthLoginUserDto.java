package auth.dto;

public class AuthLoginUserDto {

    private final String userId;
    private final String password;

    public AuthLoginUserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
