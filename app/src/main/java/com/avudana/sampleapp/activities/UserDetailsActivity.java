package com.avudana.sampleapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.avudana.sampleapp.R;
import com.avudana.sampleapp.models.User;

import butterknife.InjectView;

import static com.avudana.sampleapp.constants.Constants.USER_NAME;


public class UserDetailsActivity extends BaseActivity {

    @InjectView(R.id.tv_username_value)
    TextView tvUserNameValue;

    @InjectView(R.id.tv_phone_no_value)
    TextView tvPhoneNoValue;

    @InjectView(R.id.tv_gender_value)
    TextView tvGenderValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        String userName = getIntent().getExtras().getString(USER_NAME);

        User user = userDBDao.getUserDetails(userName);
        tvUserNameValue.setText(userName);
        tvGenderValue.setText(user.getGender());
        tvPhoneNoValue.setText(user.getPhoneNo());
    }

}
