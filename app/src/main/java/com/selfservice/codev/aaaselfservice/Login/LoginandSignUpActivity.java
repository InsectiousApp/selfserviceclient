package com.selfservice.codev.aaaselfservice.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.selfservice.codev.aaaselfservice.Fragments.LoginFragment;
import com.selfservice.codev.aaaselfservice.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class LoginandSignUpActivity extends AppCompatActivity implements View.OnClickListener{

    FancyButton bLogin, bSignUp;
    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginand_sign_up);
        getSupportActionBar().hide();

        bLogin=(FancyButton)findViewById(R.id.activity_loginand_sign_up_btn_login);
        bSignUp=(FancyButton)findViewById(R.id.activity_loginand_sign_up_btn_signup);
        bLogin.setOnClickListener(this);
        bSignUp.setOnClickListener(this);
        loginFragment=new LoginFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loginand_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_loginand_sign_up_btn_login:

                getSupportFragmentManager().beginTransaction().replace(R.id.activity_loginand_sign_up_framelayout_fragment, loginFragment).commit();

                break;
            case R.id.activity_loginand_sign_up_btn_signup:

                break;
        }
    }
}
