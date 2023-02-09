package infra.dispatcherservlet;

import infra.controller.MyController;
import infra.controller.MyGetMapping;
import infra.controller.MyPostMapping;
import infra.utils.Api;
import infra.utils.request.MyRequest;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpMethod;

public class HandlerAdaptor {

    private static final Map<Api, MethodAndController> apiMethodMap = new HashMap<>();

    public HandlerAdaptor(MyController... controllers) {
        for (MyController controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            methodMapping(controller, methods);
        }

    }

    private static void methodMapping(MyController controller, Method[] methods) {
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyGetMapping.class)) {
                getMapping(controller, method);
            }

            if (method.isAnnotationPresent(MyPostMapping.class)) {
                postMapping(controller, method);
            }
        }
    }

    private static void getMapping(MyController controller, Method method) {
        MyGetMapping annotation = method.getAnnotation(MyGetMapping.class);
        map(annotation.paths(), annotation.method(), method, controller);
    }

    private static void postMapping(MyController controller, Method method) {
        MyPostMapping annotation = method.getAnnotation(MyPostMapping.class);
        map(annotation.paths(), annotation.method(), method, controller);
    }

    private static void map(String[] apiPaths, HttpMethod httpMethod, Method method, MyController controller) {
        for (String apiPath : apiPaths) {
            apiMethodMap.put(new Api(apiPath, httpMethod), new MethodAndController(method, controller));
        }

    }


    public boolean handle(MyRequest myRequest, DataOutputStream dataOutputStream)
            throws InvocationTargetException, IllegalAccessException {
        MethodAndController methodAndController = apiMethodMap.get(myRequest.getApi());
        if (Objects.nonNull(methodAndController)) {
            methodAndController.getMethod().invoke(methodAndController.getMyController(), myRequest, dataOutputStream);
            return true;
        }
        return false;
    }


}
