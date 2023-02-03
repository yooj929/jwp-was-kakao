package controller;

import utils.MyHeaders;
import utils.MyParams;

import java.util.ArrayList;
import java.util.List;

public class FrontController {
    List<MyController> controllers = new ArrayList<>();

    void addController(MyController myController){
        controllers.add(myController);
    }

    void addAll(MyController... myControllers){
        controllers.addAll(List.of(myControllers));
    }

    void homeMapping(MyHeaders headers, MyParams params){
        controllers.stream()
                .filter((con) -> con.canHandle())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 메소드 입니다."))
                .handle(headers, params);
    }
}
