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

public class Fragment_OnBoarding_1 extends Fragment {

    public Fragment_OnBoarding_1() {
        // Required empty public constructor
    }




    TextView txtchallenge,txtanywhere,txtanytime;
    Animation animation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View view = inflater.inflate(R.layout.fragment_fragment__on_boarding_1, container, false);

        txtchallenge = view.findViewById(R.id.txtchallenge);
        txtanywhere = view.findViewById(R.id.txtanywhere);
        txtanytime = view.findViewById(R.id.txtanytime);
/*
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.left_animation);
        txtanywhere.startAnimation(animation);*/

      /*  animation = AnimationUtils.loadAnimation(getContext(), R.anim.right_anim);
        txtanytime.startAnimation(animation);*/


        fonts();

        return view;

    }
    public void fonts()
    {
        txtchallenge.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtanywhere.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Light.ttf"));
        txtanytime.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Fonts/Roboto-Bold.ttf"));

    }

}
