package com.selfservice.codev.aaaselfservice.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.selfservice.codev.aaaselfservice.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilteredBikeSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilteredBikeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilteredBikeSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Spinner spcity, spbrand, spmilage, spstyle;
    View v;
    FancyButton bSearchProducts;




    public static FilteredBikeSearchFragment newInstance(String param1, String param2) {
        FilteredBikeSearchFragment fragment = new FilteredBikeSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FilteredBikeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_filtered_bike_search, container, false);
        setAllSpinners();
        bSearchProducts=(FancyButton)v.findViewById(R.id.filteredbikesearchfragment_btn_serachproducts);
        bSearchProducts.setOnClickListener(this);

        return v;
    }

    public void onButtonPressed(String city, String brand, String milage, String style) {
        if (mListener != null) {
            mListener.onFragmentInteraction(city, brand, milage, style);
        }
    }

    private void setAllSpinners() {

        spcity=(Spinner)v.findViewById(R.id.filteredbikesearchfragment_spinner_city);
        spbrand=(Spinner)v.findViewById(R.id.filteredbikesearchfragment_spinner_brand);
        spmilage=(Spinner)v.findViewById(R.id.filteredbikesearchfragment_spinner_milage);
        spstyle=(Spinner)v.findViewById(R.id.filteredbikesearchfragment_spinner_style);

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
        ArrayAdapter<String> dataAdapterBrand = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, brandlist);
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
        ArrayAdapter<String> dataAdapterMilage=new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, milageList);
        dataAdapterMilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spmilage.setAdapter(dataAdapterMilage);

        List<String> styleList=new ArrayList<String>();
        styleList.add("scooter");
        styleList.add("motorbike");
        styleList.add("economy");
        ArrayAdapter<String> dataAdapterStyle=new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, styleList);
        dataAdapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spstyle.setAdapter(dataAdapterStyle);

        List<String> citylist=new ArrayList<String>();
        citylist.add("pune");
        citylist.add("mumbai");
        citylist.add("delhi");
        ArrayAdapter<String> dataAdapterCity=new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, citylist);
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcity.setAdapter(dataAdapterCity);



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        mListener.onFragmentInteraction(spcity.getSelectedItem().toString(), spbrand.getSelectedItem().toString(),spmilage.getSelectedItem().toString(),spstyle.getSelectedItem().toString());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(String city, String brand, String milage, String style);
    }

}
