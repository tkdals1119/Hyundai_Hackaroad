package com.sourcey.Hackaroad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.utils.ApiRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private String name;
    private String login_id;
    private String password;

    @BindView(R.id.input_name) EditText input_name;
    @BindView(R.id.input_id) EditText input_id;
    @BindView(R.id.input_password) EditText input_password;
    @BindView(R.id.btn_signup) Button btn_signup;
    @BindView(R.id.link_login) TextView link_login;


    @OnClick(R.id.input_password)
    void onClickBtn_SignUp()
    {
        name = input_name.getText().toString();
        login_id = input_id.getText().toString();
        password = input_password.getText().toString();

        if(name==null || login_id==null || password==null)
        {
            Toast.makeText(this, "정보를 기재해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Driver.getInstance().setname(name);
            Driver.getInstance().setloginid(login_id);
            Driver.getInstance().setpassword(password);
        }
        ApiRequester.getInstance().signUpDriver(Driver.getDriver(), new ApiRequester.UserCallback<Driver>() {
            @Override
            public void onSuccess(Driver result) {
                Toast.makeText(SignupActivity.this, "회원가입완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
                Toast.makeText(SignupActivity.this, "회원가입실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }
}