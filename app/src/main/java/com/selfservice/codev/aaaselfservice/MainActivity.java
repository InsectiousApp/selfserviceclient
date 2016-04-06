package com.selfservice.codev.aaaselfservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.selfservice.codev.aaaselfservice.Fragments.AadharCardVerificationFragment;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   FancyButton btnNewUser, btnExistingUser;
    AadharCardVerificationFragment aadharCardFragment;
    LinearLayout llButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Choose an option");
        aadharCardFragment=new AadharCardVerificationFragment();
        linkalltheviews();
    }

    private void linkalltheviews() {
        btnNewUser=(FancyButton)findViewById(R.id.mainactivity_btn_newuser);
        btnExistingUser=(FancyButton)findViewById(R.id.mainactivity_btn_existinguser);
        llButton=(LinearLayout)findViewById(R.id.llcontainingbuttons);
        btnNewUser.setOnClickListener(this);
        btnExistingUser.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch ((v.getId()))
        {
            case R.id.mainactivity_btn_newuser:

                if(llButton.getVisibility()==View.VISIBLE) {
                    llButton.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_framelayout, aadharCardFragment).commit();
                }

                break;

            case R.id.mainactivity_btn_existinguser:


                break;

        }

    }

    @Override
    public void onBackPressed() {
        if(llButton.getVisibility()==View.VISIBLE) {
            super.onBackPressed();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.activity_main_framelayout)).commit();
            llButton.setVisibility(View.VISIBLE);
        }

    }
}
