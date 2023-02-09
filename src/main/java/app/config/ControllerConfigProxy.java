package app.config;

import infra.controller.MyController;
import infra.controller.MyStaticController;
import infra.filter.MyFilter;
import java.util.List;
import java.util.Objects;

public class ControllerConfigProxy extends ControllerConfig {

    public static ControllerConfig INSTANCE = null;


    private ControllerConfigProxy(MyStaticController myStaticController, List<MyFilter> filters,
                                  MyController... controllers) {
        super(myStaticController, filters, controllers);
    }

    public static ControllerConfig createControllerConfigProxy(MyStaticController myStaticController,
                                                               List<MyFilter> filters,
                                                               MyController... controllers) {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new ControllerConfig(myStaticController, filters, controllers);
        }
        return INSTANCE;
    }

    public static ControllerConfig getInstance(){
        if(Objects.isNull(INSTANCE)){
            throw new IllegalStateException("Controller Config는 초기화가 필요합니다.");
        }
        return INSTANCE;
    }
}
