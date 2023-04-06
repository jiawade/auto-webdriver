package io.driver.utils;


import com.github.kevinsawicki.http.HttpRequest;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@NonNull
public class HttpRequests {
    private String host;
    private int port;
    private @NonNull HttpRequest response;
    private boolean isConnectionSetup = false;
    private int millisecond=5000;
    public @NonNull int statusCode;
    public @NonNull String text;
    public Map<String, List<String>> responseHeaders;
    public boolean isBadRequest;


    public HttpRequests() {

    }

    public HttpRequests(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public HttpRequests setProxy(String host, int port) {
        if (isConnectionSetup) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.host=host;
        this.port=port;
        return this;
    }

    public HttpRequests setProxy(Map<String, Integer> proxy) {
        if (isConnectionSetup) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        if (proxy.size() != 1) {
            throw new IllegalArgumentException("the proxy of map number must be 1");
        }
        String host=proxy.keySet().iterator().next();
        this.host=host;
        this.port=proxy.get(host);
        return this;
    }

    public HttpRequests setTimeOut(int millisecond){
        if (isConnectionSetup) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.millisecond=millisecond;
        return this;
    }

    public int getCode() {
        return statusCode;
    }

    public String getText() {
        if (!Objects.isNull(text)){
            return this.text;
        }else {
            throw new IllegalArgumentException("无法获取token");
        }

    }

    public Map<String, List<String>> getResponseHeaders(){
        return responseHeaders;
    }

    public boolean isBadRequest(){
        return isBadRequest;
    }

    public HttpRequests get(String url, Map<String, String> headers) {
        if ((host == null)) {
            response = HttpRequest.get(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.get(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }

    public HttpRequests delete(String url, Map<String, String> headers) {
        if ((host == null)) {
            response = HttpRequest.delete(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.delete(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }

    public HttpRequests post(String url, Map<String, String> headers, String body) {
        if ((host == null)) {
            response = HttpRequest.post(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers).send(body);
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.post(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers).send(body);
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }

    public HttpRequests post(String url, Map<String, String> headers) {
        if ((host == null)) {
            response = HttpRequest.post(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.post(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers);
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }

    public HttpRequests post(String url, Map<String, String> headers, Map<String, Object> body) {
        if ((host == null)) {
            response = HttpRequest.post(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers).send(GsonUtils.toJsonString(body));
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.post(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers).send(GsonUtils.toJsonString(body));
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }


    public HttpRequests put(String url, Map<String, String> headers, String body) {
        if ((host == null)) {
            response = HttpRequest.put(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers).send(body);
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.put(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers).send(body);
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }

    public HttpRequests put(String url, Map<String, String> headers, Map<String, Object> body) {
        if ((host == null)) {
            response = HttpRequest.put(url).trustAllCerts();
            response.connectTimeout(millisecond).headers(headers).send(GsonUtils.toJsonString(body));
            isConnectionSetup = true;
        } else {
            System.out.println("using proxy("+host+":"+port+") to request url:"+url);
            response = HttpRequest.put(url).trustAllCerts();
            response.useProxy(host, port).connectTimeout(millisecond).headers(headers).send(GsonUtils.toJsonString(body));
            isConnectionSetup = true;
        }
        statusCode=response.code();
        text=response.body();
        responseHeaders=response.headers();
        isBadRequest =response.badRequest();
        return this;
    }
}
