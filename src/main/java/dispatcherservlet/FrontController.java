package dispatcherservlet;

import controller.MyController;
import excpetion.NotMatchException;
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
                .findFirst().orElseThrow(() -> new NotMatchException("Cannot find handler", "Can find Handler",
                        FrontController.class.getSimpleName()));
    }
}
