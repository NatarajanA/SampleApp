package com.avudana.sampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avudana.sampleapp.R;
import com.avudana.sampleapp.db.UserDBDao;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.avudana.sampleapp.constants.Constants.REGISTER_USER;
import static com.avudana.sampleapp.constants.Constants.USER_NAME;


public class LoginActivity extends BaseActivity {

    @InjectView(R.id.et_username)
    EditText etUserName;

    @InjectView(R.id.et_password)
    EditText etPassword;

    @InjectView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public UserDBDao getDbObject(){
        return userDBDao;
    }

    @OnTextChanged({R.id.et_username, R.id.et_password})
    void onTextChanged(){
        btnLogin.setEnabled(!etUserName.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty());
    }

    @OnClick({R.id.btn_login, R.id.tv_new_user})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean isValidUser = getDbObject().authenticateUser(userName, password);
                if(isValidUser) {
                    Intent intentLogin = new Intent(context, UserDetailsActivity.class);
                    intentLogin.putExtra(USER_NAME, userName);
                    startActivity(intentLogin);
                }else{
                    Toast.makeText(context, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_new_user:
                Intent intentNewUser = new Intent(context, UserRegistrationActivity.class);
                startActivityForResult(intentNewUser, REGISTER_USER);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REGISTER_USER:
                    Toast.makeText(context, getString(R.string.user_registration_successful), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
