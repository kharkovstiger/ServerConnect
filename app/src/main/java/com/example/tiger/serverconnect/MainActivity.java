package com.example.tiger.serverconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tiger.serverconnect.models.User;
import com.example.tiger.serverconnect.providers.HttpProvider;
import com.example.tiger.serverconnect.providers.Response;
import com.example.tiger.serverconnect.tasks.AuthTask;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AuthTask.AuthListener {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private FrameLayout flMain;
    private ProgressBar pbMain;
    private String email, password;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        flMain = (FrameLayout) findViewById(R.id.flMain);
        pbMain = (ProgressBar) findViewById(R.id.pbMain);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        flMain.setOnClickListener(null);

//        pbMain.getIndeterminateDrawable().setColorFilter(0xFF0000, PorterDuff.Mode.MULTIPLY);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
        String sessionId=preferences.getString("SESSION_ID",null);

        if (sessionId!=null)
            startNextActivity(sessionId);
    }

    private void startNextActivity(String sessionId){
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("SESSION_ID",sessionId);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                login();
                break;
            case R.id.btnRegister:
                new RegistrationTask().execute();
                break;
        }
    }

    private void login() {
        flMain.setVisibility(View.VISIBLE);
        new AuthTask(this, email, password).execute();
    }

    @Override
    public void authCallBack(Response response) {
        flMain.setVisibility(View.GONE);
        if (response == null || response.getSessionId() == null)
            Toast.makeText(MainActivity.this, "Smth went wrong. Try login again.", Toast.LENGTH_SHORT).show();
        else {
            switch (response.getResponceCode()) {
                case 401:
                    Toast.makeText(MainActivity.this, "Smth went wrong. Try login again.", Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("SESSION_ID", response.getSessionId());
                    editor.commit();

                    startNextActivity(response.getSessionId());
                    break;
                default:
                    Toast.makeText(this, "Fatal error... Please, contact with support", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            try {
                response = HttpProvider.getInstance().post("{\"email\":\"" + email + "\",\"password\":\""
                        + password + "\"}", "/registration");
                Log.d("MyLog", "ResponceCode: " + response.getResponceCode() + ",\nBody: " + response.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flMain.setVisibility(View.VISIBLE);
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            flMain.setVisibility(View.GONE);
            if (response == null)
                Toast.makeText(MainActivity.this, "Smth went wrong. You are not registered.", Toast.LENGTH_SHORT).show();
            else {
                switch (response.getResponceCode()) {
                    case 406:
                        Toast.makeText(MainActivity.this, "E-mail invalid. You are not registered.", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(MainActivity.this, "User with this e-mail already exist. You are not registered.", Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        login();
                        break;
                }
            }
        }
    }
}
