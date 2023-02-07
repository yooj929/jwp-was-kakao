package controller.infra;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import utils.Api;
import utils.request.MyRequest;

public abstract class BaseMyController implements MyController{

    private final Set<Api> apis;

    public BaseMyController(Collection<Api> apis) {
        this.apis = new HashSet<>(apis);
    }

    @Override
    public boolean canHandle(MyRequest myRequest) {return apis.contains(myRequest.getApi());
    }
}
