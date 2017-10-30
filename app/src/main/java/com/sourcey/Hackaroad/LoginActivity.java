package com.sourcey.Hackaroad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.service.BackPressCloseHandler;
import com.sourcey.Hackaroad.ui.PieChartActivity;
import com.sourcey.Hackaroad.ui.TabMenuActivity;
import com.sourcey.Hackaroad.utils.ApiRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandle;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private String login_id;
    private String password;

    @BindView(R.id.login_input_id) EditText login_input_id;
    @BindView(R.id.login_input_password) EditText login_input_password;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.link_signup) TextView link_signup;

    @OnClick(R.id.btn_login)
    void onClickBtn_Login()
    {
        login_id = login_input_id.getText().toString();
        password = login_input_password.getText().toString();

        if(login_id.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "정보를 기재해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Driver.getInstance().setloginid(login_id);
            Driver.getInstance().setpassword(password);

            ApiRequester.getInstance().checkDuplicateDriver(Driver.getInstance(), new ApiRequester.UserCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    if(result)
                    {
                        Toast.makeText(LoginActivity.this, "로그인 완료!", Toast.LENGTH_SHORT).show();

                        Intent e = new Intent(LoginActivity.this, TabMenuActivity.class);
                        startActivity(e);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "등록 되어 있는 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFail() {
                }
            });
        }
    }

    @OnClick(R.id.link_signup)
    void onClickLink_SignUp()
    {
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }


    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        backPressCloseHandle = new BackPressCloseHandler(this);

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        backPressCloseHandle.onBackPressed();
    }
//
//    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);
//        finish();
//    }
//
//    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        _loginButton.setEnabled(true);
//    }
//
//    public boolean validate() {
//        boolean valid = true;
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        return valid;
//    }
}
