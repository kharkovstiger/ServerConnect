package com.example.tiger.serverconnect.tasks;

import android.os.AsyncTask;

import com.example.tiger.serverconnect.providers.HttpProvider;
import com.example.tiger.serverconnect.providers.Response;

import java.io.IOException;

public class UpdateTask extends AsyncTask<Void, Void, Response>{

    private String sessionId, json;
    private UpdateTaskListener listener;

    public UpdateTask(String sessionId, String json, UpdateTaskListener listener) {
        this.sessionId = sessionId;
        this.json = json;
        this.listener = listener;
    }

    @Override
    protected Response doInBackground(Void... params) {
        Response response=null;
        try {
            response= HttpProvider.getInstance().put(sessionId,"/me/",json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (listener!=null)
            listener.updateCallBack(response);
    }

    public interface UpdateTaskListener {
        void updateCallBack(Response response);
    }
}
