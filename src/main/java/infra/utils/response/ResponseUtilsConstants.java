package infra.utils.response;

import org.springframework.http.HttpHeaders;

public final class ResponseUtilsConstants {
    private ResponseUtilsConstants() {
    }
    public static final String HTTP_1_1_302_FOUND = "HTTP/1.1 302 Found \r\n";
    public static final String HTTP_1_1_200_OK = "HTTP/1.1 200 OK \r\n";
    public static final String LOCATION = HttpHeaders.LOCATION + ": %s \r\n";
    public static final String SET_COOKIE_JSESSIONID = HttpHeaders.SET_COOKIE + ": JSESSIONID=%s; Path=/ \r\n";
}
