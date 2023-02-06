package dispatcherservlet;

import controller.MyController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import utils.request.MyRequest;

public class FrontController {
    List<MyController> controllers;

    public FrontController(Collection<MyController> collection){
        controllers = new ArrayList<>(collection);

    }

    public MyController findHandler(MyRequest myRequest) {
        return controllers.stream()
                .filter(con -> con.canHandle(myRequest))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("지원하지 않는 url 입니다."));
    }
}
