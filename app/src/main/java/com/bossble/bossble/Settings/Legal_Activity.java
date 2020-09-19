package com.bossble.bossble.Settings;

import android.graphics.Typeface;
/*import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
import android.widget.TextView;

import com.bossble.bossble.R;

import androidx.appcompat.app.AppCompatActivity;

public class Legal_Activity extends AppCompatActivity {

    TextView txtlegal,txtdes1,txtdes2,txtdes3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_);

        txtlegal = findViewById(R.id.txtlegal);
        txtdes1 = findViewById(R.id.txtdes1);
        txtdes2 = findViewById(R.id.txtdes2);
        txtdes3 = findViewById(R.id.txtdes3);

        Fonts();

    }


    public void Fonts()
    {
        txtlegal.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtdes1.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        txtdes2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
    }

}
