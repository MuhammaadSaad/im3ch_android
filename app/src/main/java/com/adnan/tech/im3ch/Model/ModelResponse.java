package com.adnan.tech.im3ch.Model;

public class ModelResponse {
    String message, protocol, url;

    public ModelResponse(String message, String protocol, String url) {
        this.message = message;
        this.protocol = protocol;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
