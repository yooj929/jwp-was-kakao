package controller.infra;

import java.io.DataOutputStream;
import utils.request.MyRequest;

public interface MyController {

    boolean canHandle(MyRequest myRequest);

    void handle(MyRequest myRequest, DataOutputStream dataOutputStream);
}
