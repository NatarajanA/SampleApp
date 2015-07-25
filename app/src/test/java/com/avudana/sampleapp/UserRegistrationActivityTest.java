package com.avudana.sampleapp;

import android.widget.Button;
import android.widget.EditText;

import com.avudana.sampleapp.activities.UserRegistrationActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.android.api.Assertions.assertThat;

/**
 * Created by avudana on 22/07/2015.
 */
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class UserRegistrationActivityTest {

    private UserRegistrationActivity userRegistrationActivity;
    private Button btnRegister;
    private EditText etUserName;
    private EditText etPhoneNo;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Before
    public void setup() throws Exception {
        userRegistrationActivity = Robolectric.setupActivity(UserRegistrationActivity.class);
        assertNotNull("UserRegistrationActivity is not instantiated", userRegistrationActivity);
        btnRegister = (Button) userRegistrationActivity.findViewById(R.id.btn_register);
        assertThat(btnRegister).isNotNull();
        etUserName = (EditText) userRegistrationActivity.findViewById(R.id.et_username);
        assertThat(etUserName).isNotNull();
        etPhoneNo = (EditText) userRegistrationActivity.findViewById(R.id.et_phone_no);
        assertThat(etPhoneNo).isNotNull();
        etPassword = (EditText) userRegistrationActivity.findViewById(R.id.et_password);
        assertThat(etPassword).isNotNull();
        etConfirmPassword = (EditText) userRegistrationActivity.findViewById(R.id.et_confirm_password);
        assertThat(etConfirmPassword).isNotNull();
    }

    @Test
    public void registerButtonStateTest() {
        assertThat(btnRegister).isDisabled();

        etUserName.setText("    ");
        assertThat(btnRegister).isDisabled();
        etUserName.setText("username");
        assertThat(btnRegister).isDisabled();

        etPhoneNo.setText("    ");
        assertThat(btnRegister).isDisabled();
        etPhoneNo.setText("phone no");
        assertThat(btnRegister).isDisabled();

        etPassword.setText("    ");
        assertThat(btnRegister).isDisabled();
        etPassword.setText("password");
        assertThat(btnRegister).isDisabled();

        etConfirmPassword.setText("    ");
        assertThat(btnRegister).isDisabled();
        etConfirmPassword.setText("password");
        assertThat(btnRegister).isEnabled();

        btnRegister.performClick();

    }

    @Test
    public void confirmPasswordTest(){
        etUserName.setText("username");
        etPhoneNo.setText("phone no");
        etPassword.setText("password");
        etConfirmPassword.setText("confirm password");
        assertThat(btnRegister).isEnabled();
        btnRegister.performClick();

        assertEquals(userRegistrationActivity.getString(R.string.error_password_does_not_match), etConfirmPassword.getError().toString());

        etConfirmPassword.setText("password");
        btnRegister.performClick();
    }
}
