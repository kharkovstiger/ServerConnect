package com.example.tiger.serverconnect.tasks;

import android.os.AsyncTask;

import com.example.tiger.serverconnect.providers.HttpProvider;
import com.example.tiger.serverconnect.providers.Response;

import java.io.IOException;

public class GetUserInfoTask extends AsyncTask<Void, Void, Response>{

    private String sessionId;
    private GetUserInfoTaskListener listener;

    public GetUserInfoTask(String sessionId, GetUserInfoTaskListener listener) {
        this.sessionId = sessionId;
        this.listener = listener;
    }

    @Override
    protected Response doInBackground(Void... params) {
        Response response=null;
        try {
            response = HttpProvider.getInstance().get(null,sessionId,"/me");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (listener!=null)
            listener.getUserCallback(response);
    }

    public interface GetUserInfoTaskListener {
        void getUserCallback(Response response);
    }
}
