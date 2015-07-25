package com.avudana.sampleapp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.avudana.sampleapp.R;
import com.avudana.sampleapp.db.UserDBDao;

import butterknife.ButterKnife;

import static com.avudana.sampleapp.constants.Constants.REGISTER_USER;

public class BaseActivity extends Activity {

    protected Context context;
    protected UserDBDao userDBDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        userDBDao = new UserDBDao(context);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REGISTER_USER:
                Toast.makeText(context, getString(R.string.user_registration_cancelled), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
