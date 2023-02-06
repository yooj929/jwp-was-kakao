package utils.request;

import java.util.Objects;
import org.springframework.http.MediaType;

public class MyHeaders extends MyRequestMap{
    @Override
    public String get(String key){
        return Objects.requireNonNullElse(super.get(key), MediaType.TEXT_HTML_VALUE);
    }
}
