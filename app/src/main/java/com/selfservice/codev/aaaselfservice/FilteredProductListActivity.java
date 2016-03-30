package com.selfservice.codev.aaaselfservice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class FilteredProductListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ProgressDialog progressDialog;
    String mRequestBody;
    String scity, sbrand, smilage, sstyle;
    String name, brand, price, milage, style, imageURL, basePrice;
    CardView llSearch;
    FancyButton bSearchProducts;
    Spinner spcity, spbrand, spmilage, spstyle;

    ListView lv;
    LayoutInflater l;
    ArrayList<BikeItem> data;
    FilteredBikeListAdapter adapter;

    String URLtoHit;
    String QuerytoHit;
    String METHOD;


    // 1 for all products fetching
    // 2 for filtered products

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_product_list);
        llSearch=(CardView)findViewById(R.id.filteredproductlistactivity_ll_searchpart);
        bSearchProducts=(FancyButton)findViewById(R.id.filteredproductlistactivity_btn_serachproducts);
        bSearchProducts.setOnClickListener(this);
        setAllSpinners();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Obtaining Product List...");
        progressDialog.setCancelable(false);

        lv=(ListView)findViewById(R.id.filteredproductlistactivity_lv_bikelist);
        data=new ArrayList<BikeItem>();
        l=getLayoutInflater();

        adapter=new FilteredBikeListAdapter(this, 0, data, l);
        lv.setAdapter(adapter);

        progressDialog.show();


        makeserverrequest();
    }

    private void setAllSpinners() {

        spcity=(Spinner)findViewById(R.id.filteredproductlistactivity_spinner_city);
        spbrand=(Spinner)findViewById(R.id.filteredproductlistactivity_spinner_brand);
        spmilage=(Spinner)findViewById(R.id.filteredproductlistactivity_spinner_milage);
        spstyle=(Spinner)findViewById(R.id.filteredproductlistactivity_spinner_style);

        spbrand.setOnItemSelectedListener(this);
        spmilage.setOnItemSelectedListener(this);
        spstyle.setOnItemSelectedListener(this);
        spcity.setOnItemSelectedListener(this);

        List<String> brandlist = new ArrayList<String>();
        brandlist.add("yamaha");
        brandlist.add("honda");
        brandlist.add("tvs");
        brandlist.add("hero");
        brandlist.add("bajaj");
        ArrayAdapter<String> dataAdapterBrand = new ArrayAdapter<String>(this, R.layout.spinneritem1dark, brandlist);
        dataAdapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spbrand.setAdapter(dataAdapterBrand);

        List<String> milageList=new ArrayList<String>();
        milageList.add("20kmpl");
        milageList.add("30kmpl");
        milageList.add("40kmpl");
        milageList.add("50kmpl");
        milageList.add("60kmpl");
        milageList.add("70kmpl");
        milageList.add("80kmpl");
        milageList.add("90kmpl");
        milageList.add("100kmpl");
        ArrayAdapter<String> dataAdapterMilage=new ArrayAdapter<String>(this, R.layout.spinneritem1dark, milageList);
        dataAdapterMilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spmilage.setAdapter(dataAdapterMilage);

        List<String> styleList=new ArrayList<String>();
        styleList.add("scooter");
        styleList.add("motorbike");
        styleList.add("economy");
        ArrayAdapter<String> dataAdapterStyle=new ArrayAdapter<String>(this, R.layout.spinneritem1dark, styleList);
        dataAdapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spstyle.setAdapter(dataAdapterStyle);

        List<String> citylist=new ArrayList<String>();
        citylist.add("pune");
        citylist.add("mumbai");
        citylist.add("delhi");
        ArrayAdapter<String> dataAdapterCity=new ArrayAdapter<String>(this, R.layout.spinneritem1dark, citylist);
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcity.setAdapter(dataAdapterCity);



    }

    public void makeserverrequest()
    {
        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        progressDialog.show();
        String url="https://192.168.0.102:8443/fineract-provider/api/v1/users?verify&tenantIdentifier=default";
        String url2="http://192.168.0.100:9200/brands/bikes/_search";
        JSONObject jsonBody = new JSONObject();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url2
                , reqSuccessListener(), reqErrorListener()) {


            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
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
                         name=sourceObject.getString("name");
                         brand=sourceObject.getString("brand");
                        basePrice=sourceObject.getString("price");
                        basePrice=basePrice+"+Tax";
                        // price=sourceObject.getString("price");
                         milage=sourceObject.getString("mileage");
                         style=sourceObject.getString("style");


                        BikeItem b1 = new BikeItem(name, brand, basePrice, milage, style, imageURL);
                        data.add(b1);
                        adapter.notifyDataSetChanged();
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
    String url2="http://192.168.0.100:9200/brands/bikes/_search";
    JSONObject jsonBody = new JSONObject();

    JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url2
            , FilteredSearchreqSuccessListener(), FilteredSearchreqErrorListener()) {

        @Override
        public byte[] getBody() {

            Log.i("checkingg", spcity.getSelectedItem().toString()+spbrand.getSelectedItem().toString()+spmilage.getSelectedItem().toString()+spstyle.getSelectedItem().toString());

            QuerytoHit="{\n" +
                    "    \"query\": {\n" +
                    "        \"filtered\": {\n" +
                    "            \"query\": {\n" +
                    "                \"query_string\": {\n" +
                    "                    \"query\": \""+spcity.getSelectedItem().toString()+"\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"filter\": {\n" +
                    "                \"bool\":{\n" +
                    "                    \"must\":[{\n" +
                    "                        \"term\": { \"brand\": \""+spbrand.getSelectedItem().toString()+"\" }}, \n" +
                    "                {\"range\": {\"mileage\":{\n" +
                    "                    \"gte\": \"0kmpl\",\n" +
                    "                    \"lte\": \""+spmilage.getSelectedItem().toString()+"\"\n" +
                    "                }}},\n" +
                    "                {\"term\":{\"style\":\""+spstyle.getSelectedItem().toString()+"\"}}\n" +
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
                        name=sourceObject.getString("name");
                        brand=sourceObject.getString("brand");
                        basePrice=sourceObject.getString("price");
                        //basePrice=ba
                        // price=sourceObject.getString("price");
                        milage=sourceObject.getString("mileage");
                        style=sourceObject.getString("style");

                        JSONArray priceForLocationArr=sourceObject.getJSONArray("locations");

                            //means we are fetching the filtered data
                            for (int j = 0; j < priceForLocationArr.length(); j++) {
                                JSONObject priceObjectForLocation = priceForLocationArr.getJSONObject(j);
                                String cityName = priceObjectForLocation.getString("name");

                                if (cityName.contentEquals("Pune")) {
                                    price = priceObjectForLocation.getString("price");
                                    BikeItem b1 = new BikeItem(name, brand, price, milage, style, imageURL);
                                    data.add(b1);
                                    adapter.notifyDataSetChanged();
                                    break;
                                }
                            }


                        Log.i("resultt", "hits object image url  " + imageURL.toString());
                    }

                    if(data.size()>=1) {

                        Animation animFade = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
                        llSearch.startAnimation(animFade);
                        llSearch.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Filtered Products loaded sucessfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), "No Product found", Toast.LENGTH_SHORT).show();
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

            if (llSearch.getVisibility() == View.VISIBLE) {
                Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
                llSearch.startAnimation(animFade);
                llSearch.setVisibility(View.GONE);
            } else {
                //llSearch.setVisibility(View.VISIBLE);
                llSearch.setVisibility(View.VISIBLE);
                Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadein);
                llSearch.startAnimation(animFade);
            }

            return true;
        }

        if (id == R.id.filteredproductlistactivity_actionbaricon_refresh) {

            makeserverrequest();
            Animation animFade  = AnimationUtils.loadAnimation(FilteredProductListActivity.this, R.anim.fadeout);
            llSearch.startAnimation(animFade);
            llSearch.setVisibility(View.GONE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        makefilteredrequest();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
