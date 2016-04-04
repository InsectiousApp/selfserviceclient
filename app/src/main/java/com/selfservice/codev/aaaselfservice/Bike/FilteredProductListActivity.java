package com.selfservice.codev.aaaselfservice.Bike;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.selfservice.codev.aaaselfservice.Fragments.FilteredBikeSearchFragment;
import com.selfservice.codev.aaaselfservice.Models.BikeItem;
import com.selfservice.codev.aaaselfservice.R;
import com.selfservice.codev.aaaselfservice.Volley.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FilteredProductListActivity extends AppCompatActivity implements FilteredBikeSearchFragment.OnFragmentInteractionListener{

    ProgressDialog progressDialog;
    String mRequestBody;
    String scity, sbrand, smilage, sstyle;
    String name, brand, price, milage, style, imageURL, basePrice, vat="2.5", st="12.8", rto="3200", oc="1200", emi="3450", description;
    String PRICE_TO_DISPLAY;
    String ssCity, ssBrand, ssMilage, ssStyle;
    FrameLayout flsearchFragment;
    FancyButton bSearchProducts;
    FilteredBikeSearchFragment filteredBikeSearchFragment;

    ExpandableListView lv;
    LayoutInflater l;
    ArrayList<BikeItem> data;
    FilteredBikeListAdapter adapter;

    String URLtoHit;
    String QuerytoHit;
    String METHOD;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    // 1 for all products fetching
    // 2 for filtered products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_product_list);
        flsearchFragment=(FrameLayout)findViewById(R.id.filteredproductlistactivity_framelayout_flsearchfragment);

        getSupportActionBar().setTitle("Available Products");

        filteredBikeSearchFragment=new FilteredBikeSearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.filteredproductlistactivity_framelayout_flsearchfragment, filteredBikeSearchFragment).commit();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Obtaining Product List...");
        progressDialog.setCancelable(false);

        lv=(ExpandableListView)findViewById(R.id.filteredproductlistactivity_lv_bikelist);
        data=new ArrayList<BikeItem>();
        l=getLayoutInflater();

        adapter=new FilteredBikeListAdapter(this, data, l);
        lv.setAdapter(adapter);

        progressDialog.show();


        makeserverrequest();
    }

  

    public void makeserverrequest()
    {
        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        progressDialog.show();
        String url="https://192.168.0.102:8443/fineract-provider/api/v1/users?verify&tenantIdentifier=default";
        String url2="https://aws-us-east-1-portal12.dblayer.com:10536//brands/bikes/_search";
        JSONObject jsonBody = new JSONObject();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url2
                , reqSuccessListener(), reqErrorListener()) {


            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic dGhldXBzY2FsZTp1cHNjYWxlMTIz");
               // headers.put("username", "theupscale");
                //headers.put("password", "upscale123");
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

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

                data.clear();
                adapter.notifyDataSetChanged();

                Log.i("resultt", "Server response " + serverResponse.toString());
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "All Products loaded sucessfully", Toast.LENGTH_SHORT).show();

                try {

                    JSONObject objHits=serverResponse.getJSONObject("hits");
                    int totalProducts=Integer.parseInt(objHits.getString("total"));
                    JSONArray bikesArray=objHits.getJSONArray("hits");

                    for(int i=0; i<totalProducts; i++)
                    {
                        JSONObject singleBikeProduct = bikesArray.getJSONObject(i);
                        JSONObject sourceObject = singleBikeProduct.getJSONObject("_source");

                         imageURL = sourceObject.getString("image");
                         description=sourceObject.getString("description");
                         name=sourceObject.getString("name");
                         brand=sourceObject.getString("brand");
                         basePrice=sourceObject.getString("price");
                        PRICE_TO_DISPLAY=basePrice;
                        // price=sourceObject.getString("price");
                         milage=sourceObject.getString("mileage");
                         style=sourceObject.getString("style");

                        BikeItem b1 = new BikeItem(name, brand, PRICE_TO_DISPLAY, basePrice, milage, style, imageURL, vat, st, rto, oc, emi, description);
                        data.add(b1);
                        adapter.notifyDataSetChanged();
                    }
                        getSupportActionBar().setSubtitle("Total: "+totalProducts);
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
                getSupportActionBar().setSubtitle("N/A");
                progressDialog.hide();
                Log.i("resultt", "error " + error.toString());
                Toast.makeText(getApplicationContext(), "error : "+error.toString(), Toast.LENGTH_LONG).show();
            }
        };
    }







    ////////////////part for filtered search










public void makefilteredrequest()
{

    MyVolley.init(getApplicationContext());
    RequestQueue queue = MyVolley.getRequestQueue();

    progressDialog.show();
    String url="https://192.168.0.102:8443/fineract-provider/api/v1/users?verify&tenantIdentifier=default";
    String url2="https://aws-us-east-1-portal12.dblayer.com:10536//brands/bikes/_search";
    JSONObject jsonBody = new JSONObject();

    JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url2
            , FilteredSearchreqSuccessListener(), FilteredSearchreqErrorListener()) {

        @Override
        public byte[] getBody() {



            QuerytoHit="{\n" +
                    "    \"query\": {\n" +
                    "        \"filtered\": {\n" +
                    "            \"query\": {\n" +
                    "                \"query_string\": {\n" +
                    "                    \"query\": \""+ssCity+"\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"filter\": {\n" +
                    "                \"bool\":{\n" +
                    "                    \"must\":[{\n" +
                    "                        \"term\": { \"brand\": \""+ssBrand+"\" }}, \n" +
                    "                {\"range\": {\"mileage\":{\n" +
                    "                    \"gte\": \"0kmpl\",\n" +
                    "                    \"lte\": \""+ssMilage+"\"\n" +
                    "                }}},\n" +
                    "                {\"term\":{\"style\":\""+ssStyle+"\"}}\n" +
                    "                        ]\n" +
                    "                }\n" +
                    "                \n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "}";

            return QuerytoHit.getBytes();
        };

        public String getBodyContentType()
        {
            return "application/json; charset=utf-8";
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<String, String>();

            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Basic dGhldXBzY2FsZTp1cHNjYWxlMTIz");
            return headers;
        }

        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();

            return params;
        }
    };

    myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    queue.add(myReq);

}


    private com.android.volley.Response.Listener<JSONObject> FilteredSearchreqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                data.clear();
                adapter.notifyDataSetChanged();



                Log.i("resultt", "Server response " + serverResponse.toString());
                progressDialog.hide();

                Log.i("viewchecking", "dataloaded 1");

                try {

                    JSONObject objHits=serverResponse.getJSONObject("hits");
                    int totalProducts=Integer.parseInt(objHits.getString("total"));
                    JSONArray bikesArray=objHits.getJSONArray("hits");

                    Log.i("viewchecking", "dataloaded 2");

                    for(int i=0; i<totalProducts; i++)
                    {
                        JSONObject singleBikeProduct = bikesArray.getJSONObject(i);
                        JSONObject sourceObject = singleBikeProduct.getJSONObject("_source");

                        imageURL = sourceObject.getString("image");
                        description=sourceObject.getString("description");
                        name=sourceObject.getString("name");
                        brand=sourceObject.getString("brand");
                        basePrice=sourceObject.getString("price");
                        basePrice=basePrice+"+Tax";
                        milage=sourceObject.getString("mileage");
                        style=sourceObject.getString("style");

                        JSONArray priceForLocationArr=sourceObject.getJSONArray("locations");

                            //means we are fetching the filtered data
                            for (int j = 0; j < priceForLocationArr.length(); j++) {
                                JSONObject priceObjectForLocation = priceForLocationArr.getJSONObject(j);
                                String cityName = priceObjectForLocation.getString("name");

                                if (cityName.contentEquals("Pune")) {
                                    vat=priceObjectForLocation.getString("VAT");
                                    st=priceObjectForLocation.getString("ST");
                                    rto=priceObjectForLocation.getString("RTO");
                                    oc=priceObjectForLocation.getString("OC");
                                    emi=priceObjectForLocation.getString("EMI");
                                    PRICE_TO_DISPLAY=emi;
                                    BikeItem b1 = new BikeItem(name, brand, PRICE_TO_DISPLAY, basePrice, milage, style, imageURL, vat, st, rto, oc, emi, description);
                                    data.add(b1);
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                            }


                        Log.i("resultt", "hits object image url  " + imageURL.toString());
                    }

                    if(data.size()>=1) {

                        Animation animFade = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
                        flsearchFragment.startAnimation(animFade);
                        flsearchFragment.setVisibility(View.GONE);

                        getSupportActionBar().setSubtitle("Matched: " + totalProducts);
                        Toast.makeText(getApplicationContext(), "Filtered Products loaded sucessfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), "No Product found", Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setSubtitle("N/A");
                        Log.i("viewchecking", "dataloaded not loaded");
                    }

                    Log.i("viewchecking", "dataloaded finally ");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private com.android.volley.Response.ErrorListener FilteredSearchreqErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d(TAG,"in volley error");
                // Log.d(TAG, error.toString());

                getSupportActionBar().setSubtitle("N/A");
                progressDialog.hide();
                Log.i("resultt", "error " + error.toString());
                Toast.makeText(getApplicationContext(), "error : "+error.toString(), Toast.LENGTH_LONG).show();
            }
        };
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtered_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filteredproductlistactivity_actionbaricon_search) {

            if (flsearchFragment.getVisibility() == View.VISIBLE) {
                Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
                flsearchFragment.startAnimation(animFade);
                flsearchFragment.setVisibility(View.GONE);
            } else {
                //flsearchFragment.setVisibility(View.VISIBLE);
                flsearchFragment.setVisibility(View.VISIBLE);
                Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadein);
                flsearchFragment.startAnimation(animFade);
            }

            return true;
        }

        if (id == R.id.filteredproductlistactivity_actionbaricon_refresh) {

            makeserverrequest();
            Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
            flsearchFragment.startAnimation(animFade);
            flsearchFragment.setVisibility(View.GONE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(String city, String brand, String milage, String style) {

        ssCity=city;
        ssBrand=brand;
        ssMilage=milage;
        ssStyle=style;
        makefilteredrequest();

    }
}
