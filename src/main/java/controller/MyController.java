package controller;

import model.Extension;
import utils.MyHeaders;
import utils.MyParams;

import java.io.DataOutputStream;

public interface MyController {

    boolean canHandle(MyHeaders headers, MyParams params, Extension extension);

    void handle(MyHeaders headers, MyParams params, Extension extension, DataOutputStream dataOutputStream);
}
