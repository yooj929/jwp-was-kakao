package infra.controller;

import infra.utils.request.MyRequest;
import java.io.DataOutputStream;

public interface MyController {

    boolean canHandle(MyRequest myRequest);

    void handle(MyRequest myRequest, DataOutputStream dataOutputStream);
}
