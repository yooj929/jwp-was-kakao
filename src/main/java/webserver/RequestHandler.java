package webserver;

import controller.FrontController;
import model.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MyHeaders;
import utils.MyParams;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

import static db.DataBase.addUser;
import static utils.IOUtils.readData;
import static utils.ResponseHeaders.response302Header;
import static utils.UserFactory.createUser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private FrontController frontController;

    public RequestHandler(Socket connection, FrontController frontController){
        this.frontController = frontController;
        this.connection = connection;
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


            logger.info("===========================================================================================");

            String[] tokens = path.split("\\.");
            Extension extension = new Extension(tokens[tokens.length - 1]);
            String contentType = headers.get("contentType");
            if(frontController.canHandle(headers, params, extension)){
                frontController.handlerMapping(headers, params, extension, dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
