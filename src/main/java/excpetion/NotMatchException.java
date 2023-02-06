package excpetion;

import org.springframework.http.HttpStatus;

public class NotMatchException extends RuntimeException{

    private final int statusCode = HttpStatus.BAD_REQUEST.value();

    public NotMatchException(String actual, String expected, String context) {
        super(makeMessage(actual, expected, context));
    }

    private static String makeMessage(String actual, String expected, String context) {
        return String.format("\r\n"
                + "ACTUAL   : %s \r\n"
                + "EXPECTED : %s \r\n"
                + "CONTEXT  : %s \r\n", actual, expected, context);
    }
}
