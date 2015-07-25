package com.avudana.sampleapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avudana.sampleapp.activities.LoginActivity;
import com.avudana.sampleapp.activities.UserDetailsActivity;
import com.avudana.sampleapp.activities.UserRegistrationActivity;
import com.avudana.sampleapp.db.UserDBDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by avudana on 22/07/2015.
 */
@Config(constants = BuildConfig.class, sdk = 21, manifest=Config.NONE)
@RunWith(RobolectricGradleTestRunner.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPassword;
    private TextView tvNewUser;

        @Before
        public void setup() throws Exception {
            loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();
            assertNotNull("LoginActivity is not instantiated", loginActivity);

            btnLogin = (Button) loginActivity.findViewById(R.id.btn_login);
            assertThat(btnLogin).isNotNull();

            etUserName = (EditText) loginActivity.findViewById(R.id.et_username);
            assertThat(etUserName).isNotNull();

            etPassword = (EditText) loginActivity.findViewById(R.id.et_password);
            assertThat(etPassword).isNotNull();

            tvNewUser = (TextView) loginActivity.findViewById(R.id.tv_new_user);
            assertThat(tvNewUser).isNotNull();
        }

        @Test
        public void loginButtonStateTest() {
            assertThat(btnLogin).isDisabled();
            etUserName.setText("    ");
            assertThat(btnLogin).isDisabled();
            etUserName.setText("username");
            assertThat(btnLogin).isDisabled();
            etPassword.setText("    ");
            assertThat(btnLogin).isDisabled();
            etPassword.setText("password");
            assertThat(btnLogin).isEnabled();
        }

    @Test
    public void invalidCredentialsToastTest(){
        UserDBDao userDBDao = Mockito.mock(UserDBDao.class);
        doReturn(true).when(userDBDao).authenticateUser(Matchers.anyString(), Matchers.anyString());
//        when(userDBDao.authenticateUser(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
        LoginActivity logActivity = Mockito.spy(LoginActivity.class);
        doReturn(userDBDao).when(logActivity).getDbObject();
        etUserName.setText("username");
        etPassword.setText("password");
        btnLogin.performClick();
        assertEquals(ShadowToast.getTextOfLatestToast().toString(), loginActivity.getString(R.string.error_invalid_credentials));
    }

    @Test
    public void validUserLoginTest(){
        UserDBDao userDBDao = Mockito.mock(UserDBDao.class);
        when(userDBDao.authenticateUser(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
        etUserName.setText("username");
        etPassword.setText("password");
        btnLogin.performClick();
        ShadowActivity shadowActivity = shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertEquals(ShadowToast.getTextOfLatestToast().toString(), loginActivity.getString(R.string.error_invalid_credentials));
        assertEquals(UserDetailsActivity.class.getName(), intent.getComponent().getClassName());
    }

    @Test
    public void callNewUserTest(){
        tvNewUser.performClick();
        ShadowActivity shadowActivity = shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertEquals(UserRegistrationActivity.class.getName(), intent.getComponent().getClassName());
    }

}
