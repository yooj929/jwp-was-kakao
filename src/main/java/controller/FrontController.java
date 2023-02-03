package controller;

import model.Extension;
import utils.MyHeaders;
import utils.MyParams;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FrontController {
    List<MyController> controllers;
    public FrontController(){
        controllers = new ArrayList<>();
    }

    public void addController(MyController myController){
        controllers.add(myController);
    }

    public void addAll(MyController... myControllers){
        controllers.addAll(List.of(myControllers));
    }

    public void handlerMapping(MyHeaders headers, MyParams params, Extension extension, DataOutputStream dataOutputStream){
        controllers.stream()
                .filter((con) -> con.canHandle(headers, params, extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 메소드 입니다."))
                .handle(headers, params, extension, dataOutputStream);
    }

    public boolean canHandle(MyHeaders headers, MyParams params, Extension extension){
        return controllers.stream()
                .anyMatch(con -> con.canHandle(headers, params, extension));
    }
}
