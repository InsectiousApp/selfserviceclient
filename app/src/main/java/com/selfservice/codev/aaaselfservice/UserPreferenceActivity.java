package com.selfservice.codev.aaaselfservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.selfservice.codev.aaaselfservice.Bike.FilteredProductListActivity;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class UserPreferenceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spBrand, spMilage, spStyle;
    EditText etCity;
    FancyButton btnGetProducts;

    String scity, sbrand, sstyle, smilage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        spBrand=(Spinner)findViewById(R.id.userpreferenceactivity_spinner_brand);
        spMilage=(Spinner)findViewById(R.id.userpreferenceactivity_spinner_milage);
        spStyle=(Spinner)findViewById(R.id.userpreferenceactivity_spinner_style);
        etCity=(EditText)findViewById(R.id.userpreferenceactivity_et_city);
        btnGetProducts=(FancyButton)findViewById(R.id.userpreferenceactivity_btn_getproductlist);

        spBrand.setOnItemSelectedListener(this);
        spMilage.setOnItemSelectedListener(this);
        spStyle.setOnItemSelectedListener(this);
        btnGetProducts.setOnClickListener(this);

        List<String> brandlist = new ArrayList<String>();
        brandlist.add("yamaha");
        brandlist.add("honda");
        brandlist.add("TVS");
        brandlist.add("hero");
        brandlist.add("bajaj");
        ArrayAdapter<String> dataAdapterBrand = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brandlist);
        dataAdapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBrand.setAdapter(dataAdapterBrand);

        List<String> milageList=new ArrayList<String>();
        milageList.add("60kmpl");
        milageList.add("70kmpl");
        milageList.add("80kmpl");
        milageList.add("90kmpl");
        ArrayAdapter<String> dataAdapterMilage=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, milageList);
        dataAdapterMilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMilage.setAdapter(dataAdapterMilage);

        List<String> styleList=new ArrayList<String>();
        styleList.add("scooter");
        styleList.add("motorbike");
        styleList.add("economy");
        ArrayAdapter<String> dataAdapterStyle=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, styleList);
        dataAdapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStyle.setAdapter(dataAdapterStyle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_user_preference, menu);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        scity=etCity.getText().toString();
        sbrand=spBrand.getSelectedItem().toString();
        sstyle=spStyle.getSelectedItem().toString();
        smilage=spMilage.getSelectedItem().toString();

        Intent i=new Intent();
        i.setClass(getApplicationContext(), FilteredProductListActivity.class);
        i.putExtra("city",scity);
        i.putExtra("brand",sbrand);
        i.putExtra("style",sstyle);
        i.putExtra("milage",smilage);
        startActivity(i);

    }
}
