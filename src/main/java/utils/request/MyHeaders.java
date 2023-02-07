package utils.request;

import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class MyHeaders extends MyRequestMap{
    @Override
    public String get(String key) {
        if(key.equals(HttpHeaders.ACCEPT)){
            String value = getOrDefault(key, MediaType.TEXT_HTML_VALUE);
            return value.split(",")[0];
        }
        return get(key);
    }

}
