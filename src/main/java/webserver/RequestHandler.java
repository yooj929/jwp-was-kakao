package webserver;

import static utils.IOUtils.readData;

import dispatcherservlet.FrontController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Extension;
import utils.request.MyRequest;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final FrontController frontController;

    public RequestHandler(Socket connection, FrontController frontController) {
        this.connection = connection;
        this.frontController = frontController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream(); BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(in))) {
            MyRequest myRequest = new MyRequest();
            parseHeader(bufferedReader, myRequest);
            DataOutputStream dos = new DataOutputStream(out);
            parseBody(bufferedReader, myRequest);
            logger.info("===========================================================================================");
            toFrontController(myRequest, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void toFrontController(MyRequest myRequest, DataOutputStream dos) {
        String path = myRequest.getHeader("path");
        String[] tokens = path.split("\\.");
        Extension extension = new Extension(tokens[tokens.length - 1]);
        myRequest.setExtension(extension);
        if (frontController.canHandle(myRequest)) {
            frontController.handlerMapping(myRequest, dos);
        }
    }

    private void parseBody(BufferedReader br, MyRequest myRequest) throws IOException {
        if (myRequest.getHeader("method").equals("POST")) {
            String result = readData(br, Integer.parseInt(myRequest.getHeader("contentLength")));
            putBodyParams(myRequest, result);
        }
    }

    private void putBodyParams(MyRequest myRequest, String result) {
        Arrays.stream(result.split("&")).forEach(str -> {
            var keyAndValue = str.split("=");
            myRequest.putParam(keyAndValue[0], keyAndValue[1]);
        });
    }

    private void parseHeader(BufferedReader br, MyRequest myRequest) throws IOException {
        String line = br.readLine();
        putMethodAndPathAndParams(myRequest, line);
        br.readLine();
        while (!isFinalHeaderLine(line)) {
            logger.info(line);
            putContentType(myRequest, line);
            putContentLength(myRequest, line);
            line = br.readLine();
        }
    }

    private boolean isFinalHeaderLine(String line) {
        return (Objects.isNull(line) || line.equals(""));
    }

    private void putContentLength(MyRequest myRequest, String line) {
        if (line.startsWith("Content-Length: ")) {
            String contentLength = line.split(" ")[1];
            myRequest.putHeader("contentLength", contentLength);
        }
    }

    private void putContentType(MyRequest myRequest, String line) {
        if (line.startsWith("Accept: ")) {
            var tokens = line.split(" ")[1];
            String contentType = parseContentType(tokens);
            myRequest.putHeader("contentType", contentType);
        }
    }

    private static String parseContentType(String tokens) {
        if (tokens.length() == 1) {
            return tokens.split(";")[0];
        }
        if (tokens.length() > 1) {
            return tokens.split(",")[0];
        }
        throw new IllegalArgumentException("contentType이 적절하지 않습니다.");
    }

    private void putMethodAndPathAndParams(MyRequest myRequest, String line) {
        String[] tokens = line.split(" ");
        putMethod(myRequest, tokens);
        putPath(myRequest, tokens);
    }

    private void putPath(MyRequest myRequest, String[] tokens) {
        String path = tokens[1];
        if (path.contains("?")) {
            putQueryParams(myRequest, path);
            path = path.split("\\?")[0];
        }
        myRequest.putHeader("path", path);
    }

    private void putMethod(MyRequest myRequest, String[] tokens) {
        String method = tokens[0];
        myRequest.putHeader("method", method);
    }

    private void putQueryParams(MyRequest myRequest, String path) {
        Arrays.stream(path.split("\\?")[1].split("&")).forEach(str -> {
            var keyAndValue = str.split("=");
            myRequest.putParam(keyAndValue[0], keyAndValue[1]);
        });

    }
}
