package webserver;

import dispatcherservlet.FrontController;
import model.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import utils.request.MyRequest;

import static db.DataBase.addUser;
import static utils.IOUtils.readData;
import static utils.response.ResponseHeaderUtils.response302Header;
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
            MyRequest myRequest = new MyRequest();
            String line = br.readLine();
            boolean isFirstLine = false;
            // ßString path = "" , method = "", contentType= "", contentLength = "";
            while(!(Objects.isNull(line) || line.equals(""))){
                logger.info(line);

                if(!isFirstLine){
                    String[] tokens = line.split(" ");
                    String method = tokens[0];
                    myRequest.putHeader("method", method);
                    String path = tokens[1];
                    if(path.contains("?")){
                        Arrays.stream(path.split("\\?")[1].split("&")).forEach(str -> {
                            var keyAndValue = str.split("=");
                            myRequest.putParam(keyAndValue[0], keyAndValue[1]);
                        });
                        path = path.split("\\?")[0];
                    }
                    myRequest.putHeader("path", path);

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
                    myRequest.putHeader("contentType", contentType);
                }

                if(line.startsWith("Content-Length: ")){
                    String contentLength = line.split(" ")[1];
                    myRequest.putHeader("contentLength", contentLength);
                }

                line = br.readLine();
            }
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = null;
            if(myRequest.getHeader("method").equals("POST")){
                String result = readData(br, Integer.parseInt(myRequest.getHeader("contentLength")));
                Arrays.stream(result.split("&")).forEach(str -> {
                    var keyAndValue = str.split("=");
                    myRequest.putParam(keyAndValue[0], keyAndValue[1]);
                });

                // Memory DB에 유저 데이터 저장
                addUser(createUser(myRequest.getParams()));

                response302Header(dos, "/index.html");
                return;
            }
            String path = myRequest.getHeader("path");
            logger.info("===========================================================================================");

            String[] tokens = path.split("\\.");
            Extension extension = new Extension(tokens[tokens.length - 1]);
            myRequest.setExtension(extension);
            if(frontController.canHandle(myRequest)){
                frontController.handlerMapping(myRequest,dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
