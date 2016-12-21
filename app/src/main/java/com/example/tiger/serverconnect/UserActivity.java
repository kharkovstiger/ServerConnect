package com.example.tiger.serverconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tiger.serverconnect.models.User;
import com.example.tiger.serverconnect.providers.Response;
import com.example.tiger.serverconnect.tasks.AuthTask;
import com.example.tiger.serverconnect.tasks.GetUserInfoTask;
import com.example.tiger.serverconnect.tasks.UpdateTask;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, GetUserInfoTask.GetUserInfoTaskListener, UpdateTask.UpdateTaskListener {

    private EditText etFirstName, etLastName, etBDay;
    private RadioGroup rg;
    private Button btnUpdate;
    private FrameLayout flUser;
    private User user;
    private RadioButton rbMale, rbFemale;
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
    private String sessionId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        etBDay= (EditText) findViewById(R.id.etBDay);
        etFirstName= (EditText) findViewById(R.id.etFirstName);
        etLastName= (EditText) findViewById(R.id.etLastName);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        rg= (RadioGroup) findViewById(R.id.rg);
        flUser= (FrameLayout) findViewById(R.id.flUser);
        rbMale= (RadioButton) findViewById(R.id.rbMale);
        rbFemale= (RadioButton) findViewById(R.id.rbFemale);

        btnUpdate.setOnClickListener(this);

        user= (User) getIntent().getSerializableExtra("User");
        sessionId=getIntent().getStringExtra("SESSION_ID");
        if (sessionId!=null){
            flUser.setVisibility(View.VISIBLE);
            new GetUserInfoTask(sessionId,this).execute();
        }


    }

    @Override
    public void onClick(View v) {
        try {
            user.setBirthday(dateFormat.parse(etBDay.getText().toString()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        switch (rg.getCheckedRadioButtonId()){
            case R.id.rbMale:
                user.setSex(true);
                break;
            case R.id.rbFemale:
                user.setSex(false);
                break;
        }

        flUser.setVisibility(View.VISIBLE);

        Gson gson=new Gson();
        String json=gson.toJson(user);

        new UpdateTask(json,sessionId,this).execute();

        finish();
    }

    @Override
    public void getUserCallback(Response response) {
        if (response==null)
            Toast.makeText(this, "Smth went wrong. Check your i-net connection", Toast.LENGTH_SHORT).show();
        else if (response.getResponceCode()==200 && !response.getBody().equals("")){
            Gson gson = new Gson();
            User user = gson.fromJson(response.getBody(), User.class);
            etFirstName.setText(user.getFirstName());
            etLastName.setText(user.getLastName());
            if (user.isSex()!=null) {
                if (user.isSex())
                    rbMale.setChecked(true);
                else rbFemale.setChecked(true);
            }
            if (user.getBirthday()!=null)
                etBDay.setText(dateFormat.format(new Date(user.getBirthday())));
        }
        else
            Toast.makeText(this, "Fatal exception. Please, contact the support.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCallBack(Response response) {

    }
}
