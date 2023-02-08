package businuess.statics.controller;

import excpetion.NotMatchException;
import infra.utils.request.MyRequest;
import infra.utils.response.ResponseUtils;
import java.io.DataOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class StaticController {

    private final Logger logger = LoggerFactory.getLogger(StaticController.class);

    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream) {
        handleStatic(myRequest, dataOutputStream);
    }

    private void handleStatic(MyRequest request, DataOutputStream dataOutputStream) {
        try {
            ResponseUtils.make200StaticResponse(request.getPath(), request.getHeader(HttpHeaders.ACCEPT),
                    dataOutputStream, logger);
        } catch (NullPointerException nullPointerException) {
            throw new NotMatchException("api cannot match", "api must be matched",
                    StaticController.class.getSimpleName(), request.getApi());
        }
    }
}
