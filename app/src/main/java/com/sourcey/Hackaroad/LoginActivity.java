package com.sourcey.Hackaroad;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.Hackaroad.model.Driver;
import com.sourcey.Hackaroad.service.BackPressCloseHandler;
import com.sourcey.Hackaroad.ui.TabMenuActivity;
import com.sourcey.Hackaroad.utils.ApiRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences setting;
    SharedPreferences.Editor editor;
    SharedPreferences auto;
    private BackPressCloseHandler backPressCloseHandle;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private String login_id;
    private String password;
    String id, pw;

    @BindView(R.id.login_input_id) EditText login_input_id;
    @BindView(R.id.login_input_password) EditText login_input_password;
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.link_signup) TextView link_signup;

    @OnClick(R.id.btn_login)
    void onClickBtn_Login()
    {

        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        id = auto.getString("inputid", null);
        pw = auto.getString("inputpw", null);

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
                        editor.putString("Id", Driver.getInstance().getLoginid());

                        System.out.println("넣었다"+Driver.getInstance().getLoginid());
                        editor.commit();

                        if (login_id == "ny" && password == "네이버") {
                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                            //아이디가 '부르곰'이고 비밀번호가 '네이버'일 경우 SharedPreferences.Editor를 통해
                            //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                            SharedPreferences.Editor autoLogin = auto.edit();
                            autoLogin.putString("inputid", login_id);
                            autoLogin.putString("inputpw", password);
                            //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                            autoLogin.commit();
                        }

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

        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

//        if(id !=null && pw != null) {
//            if(id.equals("ny") && pw.equals("1234")) {
//                Toast.makeText(LoginActivity.this, id +"님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, Frag_ListActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
//
//        //id와 pwd가 null이면 Mainactivity가 보여짐.
//        else if(id == null && pw == null){
//            btn_login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (login_id == "ny" && password == "네이버") {
//                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
//                        //아이디가 '부르곰'이고 비밀번호가 '네이버'일 경우 SharedPreferences.Editor를 통해
//                        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
//                        SharedPreferences.Editor autoLogin = auto.edit();
//                        autoLogin.putString("id", login_id);
//                        autoLogin.putString("pw", password);
//                        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
//                        autoLogin.commit();
//                        Toast.makeText(LoginActivity.this, login_id+"님 환영합니다.", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, Frag_ListActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            });
//
//        }

        backPressCloseHandle = new BackPressCloseHandler(this);

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        backPressCloseHandle.onBackPressed();
    }
}
