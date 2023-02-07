package infra.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import utils.Api;
import utils.request.MyRequest;

public abstract class BaseMyController implements MyController{

    private final Set<Api> apis;

    public BaseMyController(BaseApis... baseApis) {
        this.apis = Arrays.stream(baseApis).map(BaseApis::getApi).collect(Collectors.toSet());
    }

    @Override
    public boolean canHandle(MyRequest myRequest) {return apis.contains(myRequest.getApi());
    }
}
