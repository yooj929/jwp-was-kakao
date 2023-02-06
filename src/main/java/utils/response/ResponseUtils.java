package utils.response;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseHeaderUtils.response200Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import utils.FileIoUtils;

public class ResponseUtils {

    public static void make200TemplatesResponse(String path, String contentType, DataOutputStream dataOutputStream,
                                                Logger logger) {
        make200Response(path, contentType, dataOutputStream, logger, "templates");
    }

    public static void make200StaticResponse(String path, String contentType, DataOutputStream dataOutputStream,
                                             Logger logger) {
        make200Response(path, contentType, dataOutputStream, logger, "static");
    }

    public static void make200Response(String path, String contentType, DataOutputStream dataOutputStream,
                                       Logger logger, String classPath) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(classPath + path);
            response200Header(dataOutputStream, contentType, body.length);
            responseBody(dataOutputStream, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
