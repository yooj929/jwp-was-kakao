package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.MyHeaders;
import utils.MyParams;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

import static db.DataBase.addUser;
import static utils.IOUtils.readData;
import static utils.UserFactory.createUser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            MyHeaders headers = new MyHeaders();
            String line = br.readLine();

            boolean isFirstLine = false;
            // ßString path = "" , method = "", contentType= "", contentLength = "";
            MyParams params = new MyParams();

            while(!(Objects.isNull(line) || line.equals(""))){
                logger.info(line);

                if(!isFirstLine){
                    String[] tokens = line.split(" ");
                    String method = tokens[0];
                    headers.put("method", method);
                    String path = tokens[1];
                    if(path.contains("?")){
                        Arrays.stream(path.split("\\?")[1].split("&")).forEach(str -> {
                            var keyAndValue = str.split("=");
                            params.put(keyAndValue[0], keyAndValue[1]);
                        });
                        path = path.split("\\?")[0];
                    }
                    headers.put("path", path);

                    isFirstLine = true;
                }
                if(line.startsWith("Accept: ")){
                    var tokens = line.split(" ")[1];
                    String contentType = null;
                    if(tokens.length() == 1){
                        contentType = tokens.split(";")[0];
                    }
                    if(tokens.length() > 1){
                        contentType = tokens.split(",")[0];
                    }
                    headers.put("contentType", contentType);
                }

                if(line.startsWith("Content-Length: ")){
                    String contentLength = line.split(" ")[1];
                    headers.put("contentLength", contentLength);
                    logger.info("CONTENT_LENGTH : {}", contentLength);
                }

                line = br.readLine();
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = null;

            if(headers.get("method").equals("POST")){
                String result = readData(br, Integer.parseInt(headers.get("contentLength")));
                Arrays.stream(result.split("&")).forEach(str -> {
                    var keyAndValue = str.split("=");
                    params.put(keyAndValue[0], keyAndValue[1]);
                });

                // Memory DB에 유저 데이터 저장
                addUser(createUser(params));

                response302Header(dos, "/index.html");
                return;
            }
            String path = headers.get("path");

            if(path.equals("/")) {
                body = "Hello world".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }
            logger.info("===========================================================================================");

            String[] tokens = path.split("\\.");
            String extension = tokens[tokens.length - 1];
            String contentType = headers.get("contentType");
            if(extension.equals("html") || extension.equals("ico")){
                body = FileIoUtils.loadFileFromClasspath("templates" + path);
                response200Header(dos, contentType, body.length);
                responseBody(dos, body);
                return;
            }
            body = FileIoUtils.loadFileFromClasspath("static" + path);
            response200Header(dos, contentType, body.length);
            responseBody(dos, body);


        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: %s", redirectUrl));
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos,String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 \r\n");
            dos.writeBytes(String.format("Content-Type: %s;charset=utf-8 \r\n", contentType));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
