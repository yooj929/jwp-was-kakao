package infra.dispatcherservlet;

import excpetion.NotMatchException;
import infra.controller.MyController;
import infra.utils.request.MyRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FrontController {
    List<MyController> controllers;

    public FrontController(MyController... collection) {
        controllers = Arrays.stream(collection).collect(Collectors.toList());
    }

    public MyController findHandler(MyRequest myRequest) {
        return controllers.stream()
                .filter(con -> con.canHandle(myRequest))
                .findFirst().orElseThrow(() -> new NotMatchException("Cannot find handler", "Can find Handler",
                        FrontController.class.getSimpleName(),myRequest.getApi()));
    }
}
