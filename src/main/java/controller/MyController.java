package controller;

import utils.MyHeaders;
import utils.MyParams;

public interface MyController {

    boolean canHandle();

    void handle(MyHeaders headers, MyParams params);
}
