package infra.utils.response;

import static infra.utils.response.ResponseBodyUtils.responseBody;
import static infra.utils.response.ResponseUtilsConstants.CONTENT_LENGTH;
import static infra.utils.response.ResponseUtilsConstants.CONTENT_TYPE;
import static infra.utils.response.ResponseUtilsConstants.HTML_EXTENSION;
import static infra.utils.response.ResponseUtilsConstants.HTTP_1_1_BASE;
import static infra.utils.response.ResponseUtilsConstants.HTTP_FOUND;
import static infra.utils.response.ResponseUtilsConstants.HTTP_OK;
import static infra.utils.response.ResponseUtilsConstants.LOCATION;
import static infra.utils.response.ResponseUtilsConstants.SET_COOKIE_JSESSIONID;
import static infra.utils.response.ResponseUtilsConstants.STATIC;
import static infra.utils.response.ResponseUtilsConstants.TEMPLATES;
import static infra.utils.response.ResponseUtilsConstants.USER_LIST;

import businuess.user.dto.UserResponseDto;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import excpetion.BaseException;
import infra.utils.FileIoUtils;
import infra.utils.request.MyCookie;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ResponseUtils {

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
            dos.writeBytes(HTTP_FOUND);
            dos.writeBytes(String.format(LOCATION, redirectUrl));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void makeCookie(DataOutputStream dos, MyCookie cookie, Logger logger) {
        try {
            dos.writeBytes(HTTP_OK);
            dos.writeBytes(String.format(SET_COOKIE_JSESSIONID, cookie.getValue()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void make302ResponseWithCookie(DataOutputStream dos, String redirectUrl, MyCookie cookie,
                                                 Logger logger) {
        try {
            dos.writeBytes(HTTP_FOUND);
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
            dos.writeBytes(HTTP_OK);
            dos.writeBytes(String.format(CONTENT_TYPE, MediaType.TEXT_HTML_VALUE));
            dos.writeBytes(String.format(CONTENT_LENGTH, lengthOfBodyContent));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent,
                                         Logger logger) {
        try {
            dos.writeBytes(HTTP_OK);
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
            loader.setPrefix("/"+TEMPLATES);
            loader.setSuffix(HTML_EXTENSION);
            Handlebars handlebars = new Handlebars(loader);
            handlebars.registerHelper("addOne", (context, options) -> (Integer) context + 1);
            Template template = handlebars.compile(USER_LIST);
            String apply = template.apply(users);
            make200Response(apply.getBytes(), contentType, dataOutputStream, logger);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void makeErrorResponse(DataOutputStream dos, Logger logger, BaseException e) {
        try {
            dos.writeBytes(String.format(HTTP_1_1_BASE, e.getStatusCode(), HttpStatus.valueOf(e.getStatusCode())));
            dos.writeBytes("\r\n");
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

    }
}
