package com.bossble.bossble.OnBoarding;

import android.graphics.Typeface;
import android.os.Bundle;
/*
import androidx.core.app.Fragment;
*/
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bossble.bossble.R;


public class Fragment_OnBorading_2 extends Fragment {


    public Fragment_OnBorading_2() {
        // Required empty public constructor
    }


    TextView txtcampaign,txtdrive,txtchange;
    Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view= inflater.inflate(R.layout.fragment_fragment__on_borading_2, container, false);

        txtcampaign = view.findViewById(R.id.txtcampaign);
        txtdrive = view.findViewById(R.id.txtdrive);
        txtchange = view.findViewById(R.id.txtchange);



        fonts();




        return view;
    }
    public void fonts()
    {
        txtcampaign.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtdrive.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Light.ttf"));
        txtchange.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));

    }
}
