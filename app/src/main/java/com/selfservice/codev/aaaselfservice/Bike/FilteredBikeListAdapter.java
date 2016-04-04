package com.selfservice.codev.aaaselfservice.Bike;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.selfservice.codev.aaaselfservice.Models.BikeItem;
import com.selfservice.codev.aaaselfservice.R;
import com.selfservice.codev.aaaselfservice.Volley.CustomVolleyRequest;

import java.util.HashMap;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


public class FilteredBikeListAdapter extends BaseExpandableListAdapter{

    LayoutInflater l;
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    List<BikeItem> objects;
    TextView tvname, tvbrand, tvprice, tvimageUrl, tvmilage, tvstyle;
    ImageView image;
    FancyButton btnMoreDetails;

    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    public FilteredBikeListAdapter(Context context, List<BikeItem> objects,
                                 LayoutInflater l) {
        Log.i("expandablelist", "constructor");
        this.context = context;
        this.objects = objects;
        this.l = l;
    }


    @Override
    public int getGroupCount() {
        Log.i("expandablelist", "grp consturctor");
        return this.objects.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i("expandablelist", "get childrencount");
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.i("expandablelist", "getGroup");
        return this.objects.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.i("expandablelist", "getchild");
        return this.objects.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.i("expandablelist", "getGroupId");
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.i("expandablelist", "getchildId");
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        Log.i("expandablelist", "hasStableIds");
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v=convertView;

        Log.i("expandablelist", "groupview");

        if(v==null)
        {
            v=l.inflate(R.layout.filteredbikelistitem, null);
        }


        tvname=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_name);
        imageView = (NetworkImageView)v. findViewById(R.id.filteredproductlistactivity_networkimageview_iv);

        final BikeItem b1=objects.get(groupPosition);
        tvname.setText(b1.name);



        imageLoader = CustomVolleyRequest.getInstance(context)
                .getImageLoader();
        imageLoader.get(b1.imageurl, ImageLoader.getImageListener(imageView,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(b1.imageurl, imageLoader);

        return v;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v=convertView;

        Log.i("expandablelist", "childview");

        if(v==null)
        {
            v=l.inflate(R.layout.filteredbikelistexpandableitem, null);
        }

        tvprice=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_price);
        tvmilage=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_milage);
        tvstyle=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_style);
        tvbrand=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_brand);
        btnMoreDetails=(FancyButton)v.findViewById(R.id.filteredproductlistactivity_btn_moredetails);
        final BikeItem b1=objects.get(groupPosition);

        tvprice.setText(b1.price);
        tvmilage.setText(b1.milage);
        tvstyle.setText(b1.style);
        tvbrand.setText(b1.brand);

        btnMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BikeLoanActivity.class);

                BikeItem b=objects.get(groupPosition);
                i.putExtra("BikeItemObjectFromBikeAdapter", b);

                context.startActivity(i);
            }
        });
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
