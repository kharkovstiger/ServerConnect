package com.example.tiger.serverconnect.providers;

public class Response {
    private int responceCode;
    private String body;
    private String sessionId;

    public int getResponceCode() {
        return responceCode;
    }

    public void setResponceCode(int responceCode) {
        this.responceCode = responceCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Response() {
    }

    public Response(int responceCode, String body, String sessionId) {
        this.responceCode = responceCode;
        this.body = body;
        this.sessionId=sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
