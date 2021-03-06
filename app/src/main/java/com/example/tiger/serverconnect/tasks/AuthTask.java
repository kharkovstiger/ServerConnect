package com.example.tiger.serverconnect.tasks;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.tiger.serverconnect.providers.HttpProvider;
import com.example.tiger.serverconnect.providers.Response;

import java.io.IOException;

public class AuthTask extends AsyncTask<Void, Void, Response>{
    private AuthListener listener;
    private String email;
    private String password;

    public AuthTask(AuthListener listener, String email, String password) {
        this.listener = listener;
        this.email = email;
        this.password = password;
    }

    @Override
    protected Response doInBackground(Void... params) {
        Response response=null;
        final String basicAuth = "Basic " + Base64.encodeToString((email+":"+password).getBytes(), Base64.NO_WRAP);
        try {
            response = HttpProvider.getInstance().get(basicAuth,null,"/me");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (listener!=null)
            listener.authCallBack(response);
    }

    public interface AuthListener {
        void authCallBack(Response response);
    }
}
