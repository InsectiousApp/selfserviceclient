package com.selfservice.codev.aaaselfservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Codev on 3/29/2016.
 */
public class FilteredBikeListAdapter extends ArrayAdapter<BikeItem>{

    LayoutInflater l;
    Context context;
    List<BikeItem> objects;
    TextView tvname, tvbrand, tvprice, tvimageUrl, tvmilage, tvstyle;
    ImageView image;

    public FilteredBikeListAdapter(Context context, int resource, List<BikeItem> objects, LayoutInflater l) {
        super(context, resource, objects);
        this.l=l;
        this.context=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;

        if(v==null)
        {
            v=l.inflate(R.layout.filteredbikelistitem, null);
        }

        tvname=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_name);
        tvprice=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_price);
        tvmilage=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_milage);
        tvstyle=(TextView)v.findViewById(R.id.filteredproductlistactivity_tv_style);

        final BikeItem b1=objects.get(position);
        tvname.setText(b1.name);
        tvprice.setText(b1.price);
        tvmilage.setText(b1.milage);
        tvstyle.setText(b1.style);

        return v;
    }
}
