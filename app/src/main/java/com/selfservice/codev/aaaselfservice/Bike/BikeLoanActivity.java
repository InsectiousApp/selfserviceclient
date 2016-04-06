package com.selfservice.codev.aaaselfservice.Bike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.selfservice.codev.aaaselfservice.Login.LoginandSignUpActivity;
import com.selfservice.codev.aaaselfservice.Models.BikeItem;
import com.selfservice.codev.aaaselfservice.R;
import com.selfservice.codev.aaaselfservice.Volley.CustomVolleyRequest;

public class BikeLoanActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    TextView tvMilage, tvBrand, tvDescription;
    TextView tvBasePrice, tvVatAmount, tvVatFinal, tvSTAmount, tvSTFinal, tvRTOAmount, tvRTOFinal, tvOCAmount, tvOCFinal, tvTotalFinal;
    TextView tvApplyforLoan;
    BikeItem b1;
    int ibasePrice, irto, iothers, itotal;
    Float fvat, fst;
    float vatAmount, stAmount, rtoAmount, ocAmount, totalAmount;

    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_loan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        toolbarTextAppernce();

        initializeandlinkViews();

        Intent i=getIntent();
        b1=(BikeItem)i.getSerializableExtra("BikeItemObjectFromBikeAdapter");

        calculateAllTaxes();
        setAllTextViews();
        fetchImageFromUrl();
    }




    private void initializeandlinkViews() {
        tvMilage=(TextView)findViewById(R.id.activity_bike_loan_bikedetail_mileage);
        tvBrand=(TextView)findViewById(R.id.activity_bike_loan_bikedetail_brand);
        tvDescription=(TextView)findViewById(R.id.activity_bike_loan_bikedetail_description);

        tvBasePrice=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_baseprice_final);

        tvVatAmount=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_vat_amount);
        tvVatFinal=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_vat_final);

        tvSTAmount=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_st_amount);
        tvSTFinal=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_st_final);

        tvRTOAmount=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_rto_amount);
        tvRTOFinal=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_rto_final);

        tvOCAmount=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_others_amount);
        tvOCFinal=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_others_final);

        tvTotalFinal=(TextView)findViewById(R.id.activity_bike_loan_biketaxcost_total_final);

        imageView = (NetworkImageView)findViewById(R.id.activity_bike_loan_networkimageview_ivbikeimage);

        tvApplyforLoan=(TextView)findViewById(R.id.activity_bike_loan_bikeloanapply_applyforloan);
        tvApplyforLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent();
                    i.setClass(getApplicationContext(), LoginandSignUpActivity.class);
                    startActivity(i);
            }
        });
    }

    private void calculateAllTaxes() {
        ibasePrice=Integer.parseInt(b1.baseprice);
        fvat=Float.parseFloat(b1.VAT);
        fst=Float.parseFloat(b1.ST);
        irto=Integer.parseInt(b1.RTO);
        iothers=Integer.parseInt(b1.OC);
    }

    private void setAllTextViews() {

        collapsingToolbarLayout.setTitle(b1.name);
        //collapsingToolbarLayout.setExpandedTitleColor(R.color.orange2);
        //collapsingToolbarLayout.setCollapsedTitleTextColor(R.color.orange2);
        tvMilage.setText(b1.milage);
        tvBrand.setText(b1.brand);
        tvDescription.setText(b1.description);

        tvBasePrice.setText(b1.baseprice+"/-");

        tvVatAmount.setText(fvat+"%");
        vatAmount=(fvat*ibasePrice)/100;
        tvVatFinal.setText(vatAmount+"/-");

        tvSTAmount.setText(fst+"%");
        stAmount=((fst*ibasePrice)/100);
        tvSTFinal.setText(stAmount+"/-");

        tvRTOAmount.setText(irto+"/-");
        tvRTOFinal.setText(irto+"/-");

        tvOCAmount.setText(iothers+"/-");
        tvOCFinal.setText(iothers+"/-");

        tvTotalFinal.setText(vatAmount+stAmount+irto+iothers+ibasePrice+"/-");
    }

    private void fetchImageFromUrl() {
        imageLoader = CustomVolleyRequest.getInstance(getApplicationContext())
                .getImageLoader();
        imageLoader.get(b1.imageurl, ImageLoader.getImageListener(imageView,
                R.mipmap.bikeimage1, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(b1.imageurl, imageLoader);

        dynamicToolbarColor();

    }

    private void dynamicToolbarColor() {

       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                //imageView);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

    private void toolbarTextAppernce() {
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bike_loan, menu);
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
}
