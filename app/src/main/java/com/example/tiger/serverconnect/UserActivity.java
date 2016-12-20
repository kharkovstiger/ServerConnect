package com.example.tiger.serverconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tiger.serverconnect.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirstName, etLastName, etBDay;
    RadioGroup rg;
    Button btnUpdate;
    FrameLayout flUser;
    User user;
    RadioButton rbMale, rbFemale;
    final SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");

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

        finish();
    }
}
