package dispatcherservlet;

import controller.MyController;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Extension;
import utils.MyHeaders;
import utils.MyParams;
import utils.request.MyRequest;

public class FrontController {
    List<MyController> controllers;

    public FrontController() {
        controllers = new ArrayList<>();
    }

    public void addController(MyController myController) {
        controllers.add(myController);
    }

    public void addAll(MyController... myControllers) {
        controllers.addAll(List.of(myControllers));
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
