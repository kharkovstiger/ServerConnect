package com.example.tiger.serverconnect.providers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpProvider {

    final static private String baseURL="http://35.156.187.247:8080/api/v1";

    private static HttpProvider ourInstance = new HttpProvider();

    public static HttpProvider getInstance() {
        return ourInstance;
    }

    private HttpProvider() {
    }

    public Response post(String json, String path) throws IOException {
        Response response =new Response();
        URL url=new URL(baseURL+path);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
        writer.write(json);
        writer.flush();
        writer.close();

        String body="";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;
            while ((line = reader.readLine()) != null) {
                body += line;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        response.setResponceCode(connection.getResponseCode());
        response.setBody(body);

        return response;
    }

    public Response put(String json, String path, String sessionId) throws IOException {
        Response response =new Response();
        URL url=new URL(baseURL+path);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Cookie", sessionId);

        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
        writer.write(json);
        writer.flush();
        writer.close();

        String body="";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;
            while ((line = reader.readLine()) != null) {
                body += line;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        response.setResponceCode(connection.getResponseCode());
        response.setBody(body);

        return response;
    }

    public Response get(String token, String sesionId, String path) throws IOException {
        Response response =new Response();
        URL url=new URL(baseURL+path);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Cookie", sesionId);
        connection.setRequestProperty("Authorization", token);
        connection.connect();

        if (token!=null) {
            response.setSessionId(connection.getHeaderField("Set-Cookie"));
        }

        String body="";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            String line;
            while ((line = reader.readLine()) != null) {
                body += line;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        response.setResponceCode(connection.getResponseCode());
        response.setBody(body);

        return response;
    }
}
