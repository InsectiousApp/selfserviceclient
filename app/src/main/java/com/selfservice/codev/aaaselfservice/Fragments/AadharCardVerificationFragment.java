package com.selfservice.codev.aaaselfservice.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.selfservice.codev.aaaselfservice.Bike.FilteredProductListActivity;
import com.selfservice.codev.aaaselfservice.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class AadharCardVerificationFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static AadharCardVerificationFragment newInstance(String param1, String param2) {
        AadharCardVerificationFragment fragment = new AadharCardVerificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AadharCardVerificationFragment() {
        // Required empty public constructor
    }


    FancyButton btnVerify;
    View v;
    EditText etAadharCardNumber;
    String aadharCardNumber;

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
       v=inflater.inflate(R.layout.fragment_aadhar_card_verification, container, false);

        btnVerify=(FancyButton)v.findViewById(R.id.fragment_aadhar_card_verification_btn_verify);
        etAadharCardNumber=(EditText)v.findViewById(R.id.fragment_aadhar_card_verification_et_aadharcardnumber);
        btnVerify.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragment_aadhar_card_verification_btn_verify:
                 aadharCardNumber=etAadharCardNumber.getText().toString();
                if (aadharCardNumber != null && !aadharCardNumber.isEmpty()&&(aadharCardNumber.length()==12)) {
                    Intent i=new Intent();
                    i.setClass(getActivity(), FilteredProductListActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity(), "Please enter 12-digit aadhar card number", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
