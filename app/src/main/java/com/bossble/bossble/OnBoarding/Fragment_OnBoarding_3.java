package com.bossble.bossble.OnBoarding;

import android.graphics.Typeface;
import android.os.Bundle;
/*
import androidx.core.app.Fragment;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bossble.bossble.R;

import androidx.fragment.app.Fragment;


public class Fragment_OnBoarding_3 extends Fragment {


    public Fragment_OnBoarding_3() {
        // Required empty public constructor
    }



    TextView txtboss,txttalent,txtshowcase;
    Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_fragment__on_boarding_3, container, false);

        txtboss = view.findViewById(R.id.txtboss);
        txttalent = view.findViewById(R.id.txttalent);
        txtshowcase = view.findViewById(R.id.txtshowcase);

        fonts();


        return view;
    }
    public void fonts()
    {
        txtboss.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));
        txttalent.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtshowcase.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Light.ttf"));

    }


}
