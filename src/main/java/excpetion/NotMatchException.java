package excpetion;

import org.springframework.http.HttpStatus;

public class NotMatchException extends BaseException{
    public NotMatchException(String actual, String expected, String context) {
        super(actual, expected, context, HttpStatus.BAD_REQUEST.value());
    }


}
