package com.selfservice.codev.aaaselfservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.selfservice.codev.aaaselfservice.Volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class CreateClientActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etusername, etfirstname, etlastname, etemailid, etmobileno;
    FancyButton bregister;
    ProgressDialog progressDialog;
    CheckBox cb;
    String mRequestBody;


    String username, firstname, lastname, emailid, mobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);
        getSupportActionBar().hide();

        etusername=(EditText)findViewById(R.id.createclientactivity_et_username);
        etfirstname=(EditText)findViewById(R.id.createclientactivity_et_firstname);
        etlastname=(EditText)findViewById(R.id.createclientactivity_et_lastname);
        etemailid=(EditText)findViewById(R.id.createclientactivity_et_emailid);
        etmobileno=(EditText)findViewById(R.id.createclientactivity_et_mobileno);
        bregister=(FancyButton)findViewById(R.id.createclientactivity_btn_register);
        cb=(CheckBox)findViewById(R.id.createclientactivity_cb_agreeterms);

        bregister.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering....");
        progressDialog.setCancelable(false);
        progressDialog.hide();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_client, menu);
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

        makeserverrequest();

        if(cb.isChecked())
        {

            username=etusername.getText().toString();
            firstname=etfirstname.getText().toString();
            mobileno=etmobileno.getText().toString();
            lastname=etlastname.getText().toString();
            emailid=etemailid.getText().toString();

            if (username != null && !username.isEmpty()&&firstname != null && !firstname.isEmpty()&&mobileno != null && !mobileno.isEmpty()&&emailid != null && !emailid.isEmpty()&&lastname != null && !lastname.isEmpty())
            {

            }
            else
            {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this, "Please agree the terms", Toast.LENGTH_SHORT).show();
        }


    }


    public void makeserverrequest()
    {


        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        // String idd=etClientId.getText().toString();



        progressDialog.show();
        String url="https://192.168.0.102:8443/fineract-provider/api/v1/users?verify&tenantIdentifier=default";
        String url2="http://192.168.0.102:9200/brands/bikes/_search";
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("username", username);
            jsonBody.put("firstname", firstname);
            jsonBody.put("lastname", lastname);
            jsonBody.put("email", emailid);
            jsonBody.put("mobile", "+91"+mobileno);
            jsonBody.put("sendPasswordToEmail", true);
            jsonBody.put("sendCodeToMobile", true);
            jsonBody.put("isSelfServiceUser", true);

//
//            Random crazy=new Random();
//            String randomnumber1=String.valueOf(crazy.nextInt(978)+1);
//            String randomnumber2=String.valueOf(crazy.nextInt(7)+1);
//
//            String externalIdd=randomnumber1+"YJJ"+randomnumber2;
//
//            jsonBody.put("externalId", externalIdd);

//            Log.i("resultt", "external id " + externalIdd);
//            Log.i("resultt", "name "+name);
//            Log.i("resultt", "mobile no "+mobileno);
//            Log.i("resultt", "father name "+fathername);

//            jsonBody.put("dateFormat", "dd MMMM yyyy");
//            jsonBody.put("locale", "en");
//            jsonBody.put("active", false);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRequestBody = jsonBody.toString();
        Log.i("resultt", "bodyy "+mRequestBody.toString());

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url2
                , reqSuccessListener(), reqErrorListener()) {



            @Override
            public byte[] getBody() {

                //String str = "{\"officeId\":\""+officeid+"\",\"firstname\":\""+firstname+"\",\"lastname\":\"\""+lastname+"\"\",\"fathername\":\"\""+fathername+"\"\"}";

                String query1="{\n" +
                        "    \"query\": {\n" +
                        "        \"filtered\": {\n" +
                        "            \"query\": {\n" +
                        "                \"query_string\": {\n" +
                        "                    \"query\": \"pune\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"filter\": {\n" +
                        "                \"bool\":{\n" +
                        "                    \"must\":[{\n" +
                        "                        \"term\": { \"brand\": \"honda\" }}, \n" +
                        "                {\"range\": {\"mileage\":{\n" +
                        "                    \"gte\": \"0kmpl\",\n" +
                        "                    \"lte\": \"80kmpl\"\n" +
                        "                }}},\n" +
                        "                {\"term\":{\"style\":\"scooter\"}}\n" +
                        "                        ]\n" +
                        "                }\n" +
                        "                \n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "}";

                return query1.getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                SharedPreferences sharedPreferencesForAuthentication =getSharedPreferences("AUTHENTICATION_KEY", Context.MODE_PRIVATE);
                String baseauthKey=sharedPreferencesForAuthentication.getString("authentication_key", "xx");


                //headers.put("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ=");
                headers.put("Content-Type", "application/json");

                // headers.put("password", "password");

                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();



                // params.put("key", table2key);
                //     params.put("username", "mifos");
                //   params.put("password", "password");
                // params.put("tenantIdentifier", "default");

                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);




    }

    private com.android.volley.Response.Listener<JSONObject> reqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                Log.i("resultt", "Server response " + serverResponse.toString());
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();

                try {

                    JSONObject objHits=serverResponse.getJSONObject("hits");
                    int totalProducts=Integer.parseInt(objHits.getString("total"));
                    JSONArray bikesArray=objHits.getJSONArray("hits");

                    for(int i=0; i<totalProducts; i++)
                    {
                        JSONObject singleBikeProduct = bikesArray.getJSONObject(i);
                        JSONObject sourceObject = singleBikeProduct.getJSONObject("_source");
                        String imageURL = sourceObject.getString("image");
                        JSONArray priceForLocationArr=sourceObject.getJSONArray("locations");

                        for(int j=0; j<priceForLocationArr.length(); j++)
                        {
                            JSONObject priceObjectForLocation=priceForLocationArr.getJSONObject(j);
                            String cityName=priceObjectForLocation.getString("name");

                            if(cityName.contentEquals("Pune"))
                            {
                                String priceee=priceObjectForLocation.getString("price");
                                Log.i("resultt", "priceee  " + priceee);
                            }
                        }

                        Log.i("resultt", "hits object image url  " + imageURL.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d(TAG,"in volley error");
                // Log.d(TAG, error.toString());
                progressDialog.hide();
                Log.i("resultt", "error " + error.toString());
                Toast.makeText(getApplicationContext(), "Can't Register", Toast.LENGTH_LONG).show();
            }
        };
    }




}
