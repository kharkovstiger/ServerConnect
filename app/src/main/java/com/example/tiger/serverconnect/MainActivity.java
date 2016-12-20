package com.example.tiger.serverconnect;

import android.content.Intent;
import android.graphics.PorterDuff;
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
import com.example.tiger.serverconnect.providers.Responce;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private FrameLayout flMain;
    private ProgressBar pbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        flMain= (FrameLayout) findViewById(R.id.flMain);
        pbMain= (ProgressBar) findViewById(R.id.pbMain);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        flMain.setOnClickListener(null);

//        pbMain.getIndeterminateDrawable().setColorFilter(0xFF0000, PorterDuff.Mode.MULTIPLY);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogin:
                break;
            case R.id.btnRegister:
                new RegistrationTask().execute();
                break;
        }
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Responce>{

        String email, password;

        @Override
        protected Responce doInBackground(Void... params) {
            Responce responce= null;
            try {
                responce = HttpProvider.getInstance().post("{\"email\":\""+email+"\",\"password\":\""
                        +password+"\"}","/registration");
                Log.d("MyLog","ResponceCode: "+responce.getResponceCode()+",\nBody: "+responce.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responce;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flMain.setVisibility(View.VISIBLE);
            email=etEmail.getText().toString();
            password=etPassword.getText().toString();
        }

        @Override
        protected void onPostExecute(Responce responce) {
            super.onPostExecute(responce);
            flMain.setVisibility(View.GONE);
            if (responce==null)
                Toast.makeText(MainActivity.this, "Smth went wrong. Check your i-net connection.", Toast.LENGTH_SHORT).show();
            else {
                switch (responce.getResponceCode()){
                    case 406:
                        Toast.makeText(MainActivity.this, "E-mail invalid", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        Gson gson = new Gson();
                        User user = gson.fromJson(responce.getBody(), User.class);
                        Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,UserActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        break;
                }
            }
        }
    }
}
