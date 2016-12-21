package com.example.tiger.serverconnect.tasks;

import android.os.AsyncTask;

import com.example.tiger.serverconnect.providers.Response;

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
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (listener!=null)
            ;
    }

    public interface AuthListener {
        void authCallBack(Response response);
    }
}
