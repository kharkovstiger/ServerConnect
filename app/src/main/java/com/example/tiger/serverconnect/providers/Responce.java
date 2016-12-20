package com.example.tiger.serverconnect.providers;

public class Responce {
    private int responceCode;
    private String body;

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

    public Responce() {
    }

    public Responce(int responceCode, String body) {
        this.responceCode = responceCode;
        this.body = body;
    }
}
