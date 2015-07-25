package com.avudana.sampleapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.avudana.sampleapp.R;
import com.avudana.sampleapp.models.User;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class UserRegistrationActivity extends BaseActivity {

    @InjectView(R.id.et_username)
    EditText etUserName;

    @InjectView(R.id.et_phone_no)
    EditText etPhoneNo;

    @InjectView(R.id.et_password)
    EditText etPassword;

    @InjectView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @InjectView(R.id.btn_register)
    Button btnRegister;

    @InjectView(R.id.rg_gender)
    RadioGroup rgGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
    }

    @OnTextChanged({R.id.et_username, R.id.et_phone_no, R.id.et_password, R.id.et_confirm_password})
    void onTextChanged() {
        btnRegister.setEnabled(areAllFieldsEntered());
    }

    @OnClick(R.id.btn_register)
    void onRegisterBtnClicked() {
        if(etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            User user = new User();
            user.setUserName(etUserName.getText().toString().trim());
            user.setPassword(etPassword.getText().toString().trim());
            user.setPhoneNo(etPhoneNo.getText().toString().trim());
            user.setGender(((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString());
            long insertedId = userDBDao.insertUser(user);
            if (insertedId != -1) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(context, getString(R.string.error_user_already_exists), Toast.LENGTH_SHORT).show();
            }
        }else {
            etConfirmPassword.setError(getString(R.string.error_password_does_not_match));
        }
    }

    private boolean areAllFieldsEntered() {
        return !etUserName.getText().toString().trim().isEmpty() && !etPhoneNo.getText().toString().trim().isEmpty()
                && !etPassword.getText().toString().trim().isEmpty() && !etConfirmPassword.getText().toString().trim().isEmpty();
    }
}
