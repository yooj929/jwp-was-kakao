package utils.request;

import utils.Extension;
import utils.MyHeaders;
import utils.MyParams;
public class MyRequest {
    private final MyParams params;
    private final MyHeaders headers;

    private Extension extension;

    public MyRequest() {
        this.params = new MyParams();
        this.headers = new MyHeaders();
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void putParam(String key, String value) {
        params.put(key, value);
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public boolean isStatic() {
        return extension.isStatic();
    }

    public MyParams getParams() {
        return params;
    }

    public MyHeaders getHeaders() {
        return headers;
    }
}
