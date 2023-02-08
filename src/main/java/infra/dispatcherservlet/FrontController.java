package infra.dispatcherservlet;

import static infra.utils.response.ResponseUtils.makeErrorResponse;

import businuess.statics.controller.StaticController;
import excpetion.NotMatchException;
import infra.controller.MyController;
import infra.utils.request.MyRequest;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontController {

    private final HandlerAdaptor handlerAdaptor;
    private final StaticController staticController;
    private final Logger logger = LoggerFactory.getLogger(FrontController.class);

    public FrontController(StaticController staticController, MyController... controllers) {
        this.staticController = staticController;
        this.handlerAdaptor = new HandlerAdaptor(controllers);
    }

    public void handle(MyRequest myRequest, DataOutputStream dataOutputStream)
            throws InvocationTargetException, IllegalAccessException {
        boolean handled = handlerAdaptor.handle(myRequest, dataOutputStream);
        if(!handled){
            handleStatic(myRequest, dataOutputStream);
        }
    }

    private void handleStatic(MyRequest myRequest, DataOutputStream dataOutputStream) {
        try {
            staticController.handle(myRequest, dataOutputStream);
        }catch(NotMatchException e){
            makeErrorResponse(dataOutputStream, logger, e);
            logger.error(e.getMessage());
        }
    }

}
