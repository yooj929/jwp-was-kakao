package dispatcherservlet;

import controller.MyController;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import utils.request.MyRequest;

public class FrontController {
    List<MyController> controllers;

    public FrontController(Collection<MyController> collection){
        controllers = new ArrayList<>(collection);

    }

    public void handlerMapping(MyRequest myRequest, DataOutputStream dataOutputStream) {
        controllers.stream()
                .filter((con) -> con.canHandle(myRequest))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 메소드 입니다."))
                .handle(myRequest, dataOutputStream);
    }

    public boolean canHandle(MyRequest myRequest) {
        return controllers.stream()
                .anyMatch(con -> con.canHandle(myRequest));
    }
}
