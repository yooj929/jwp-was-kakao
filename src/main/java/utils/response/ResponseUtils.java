package utils.response;

import static utils.response.ResponseBodyUtils.responseBody;
import static utils.response.ResponseUtilsConstants.HTTP_1_1_200_OK;
import static utils.response.ResponseUtilsConstants.HTTP_1_1_302_FOUND;
import static utils.response.ResponseUtilsConstants.LOCATION;
import static utils.response.ResponseUtilsConstants.SET_COOKIE_JSESSIONID;

import auth.controller.MyCookie;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import user.dto.UserResponseDto;
import utils.FileIoUtils;

public class ResponseUtils {

    private static final String TEMPLATES = "templates";
    private static final String STATIC = "static";
    public static final String CONTENT_LENGTH = HttpHeaders.CONTENT_LENGTH + ": %d \r\n";
    public static final String CONTENT_TYPE = HttpHeaders.CONTENT_TYPE + ": %s;charset=utf-8 \r\n";

    public static void make200TemplatesResponse(String path, String contentType, DataOutputStream dataOutputStream,
                                                Logger logger) {
        make200Response(path, contentType, dataOutputStream, logger, TEMPLATES);
    }


    public static void make200StaticResponse(String path, String contentType, DataOutputStream dataOutputStream,
                                             Logger logger) {
        make200Response(path, contentType, dataOutputStream, logger, STATIC);
    }

    public static void make302ResponseHeader(DataOutputStream dos, String redirectUrl, Logger logger) {
        try {
            dos.writeBytes(HTTP_1_1_302_FOUND);
            dos.writeBytes(String.format(LOCATION, redirectUrl));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void makeCookie(DataOutputStream dos, MyCookie cookie, Logger logger) {
        try {
            dos.writeBytes(HTTP_1_1_200_OK);
            dos.writeBytes(String.format(SET_COOKIE_JSESSIONID, cookie.getValue()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void make302ResponseWithCookie(DataOutputStream dos, String redirectUrl, MyCookie cookie,
                                                 Logger logger) {
        try {
            dos.writeBytes(HTTP_1_1_302_FOUND);
            dos.writeBytes(String.format(LOCATION, redirectUrl));
            makeCookie(dos, cookie, logger);
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void make200Response(String path, String contentType, DataOutputStream dataOutputStream,
                                       Logger logger, String classPath) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(classPath + path);
            response200Header(dataOutputStream, contentType, body.length, logger);
            responseBody(dataOutputStream, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void make200Response(byte[] body, String contentType, DataOutputStream dataOutputStream,
                                       Logger logger) {
        response200Header(dataOutputStream, contentType, body.length, logger);
        responseBody(dataOutputStream, body);
    }

    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent, Logger logger) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent,
                                         Logger logger) {
        try {
            dos.writeBytes(HTTP_1_1_200_OK);
            dos.writeBytes(String.format(CONTENT_TYPE, contentType));
            dos.writeBytes(String.format(CONTENT_LENGTH, lengthOfBodyContent));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void make200ResponseWithUsersByHandleBars(String contentType, DataOutputStream dataOutputStream,
                                                            List<UserResponseDto> users, Logger logger) {
        try {
            ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("addOne", (context, options) -> (Integer) context + 1);
            Template template = handlebars.compile("/user/list");
            String apply = template.apply(users);
            make200Response(apply.getBytes(), contentType, dataOutputStream, logger);
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

}
