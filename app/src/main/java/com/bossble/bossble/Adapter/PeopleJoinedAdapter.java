package com.bossble.bossble.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
/*
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.People_joined;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class PeopleJoinedAdapter extends RecyclerView.Adapter<PeopleJoinedAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<People_joined> mpeople=new ArrayList<>();
    Button mjoin;
    Activity mactivity;
    String mchallenge_id;
    String mctype;
    private AVLoadingIndicatorView mavi;
    ImageView mavibackground;
    String mchallenge_title;
    String muserid1;
    String muserid2;
    String muserid3;
    String museridd;
    String mjoinuser;
    String mmode;
    LinearLayout mlinearjoin;


    public PeopleJoinedAdapter(Context mcontext ,ArrayList<People_joined> images,Button join, Activity activity,String challenge_id,String ctype,AVLoadingIndicatorView avi,ImageView avibackground,String challenge_title,
                               String userid1,String userid2,String userid3,String useridd,String joinuser,String mode,LinearLayout linearjoin){

        this.mcontext = mcontext;
        mpeople=images;
        mjoin = join;
        mactivity = activity;
        mchallenge_id = challenge_id;
        mctype = ctype;
        mavi = avi;
        mavibackground = avibackground;
        mchallenge_title = challenge_title;

        muserid1 = userid1;
        muserid2 = userid2;
        muserid3 = userid3;
        museridd = useridd;
        mjoinuser = joinuser;
        mmode = mode;

        mlinearjoin = linearjoin;
    }


    @NonNull
    @Override
    public PeopleJoinedAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.peoplejoined_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final PeopleJoinedAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final People_joined people_joined = mpeople.get(i);

        RequestOptions options2 = new RequestOptions();
        options2.centerCrop();
        options2.placeholder(R.drawable.bossble_placeholder_large);
        Glide.with(mcontext)
                .load(people_joined.getProfile_image())
                .apply(options2)
                .into(viewHolderForCat.image);



        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mmode.equals("no")){
                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Signintoexplore));

                }
                else {
                    Intent intent = new Intent(mcontext,Details_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",people_joined.getId());
                    intent.putExtra("username",people_joined.getUsername());
                    intent.putExtra("userimage",people_joined.getProfile_image());
                    intent.putExtra("user_id",people_joined.getUser_id());
                    intent.putExtra("image","");
                    intent.putExtra("likes",people_joined.getLike_count());
                    intent.putExtra("comments",people_joined.getComments_count());
                    intent.putExtra("title",people_joined.getTitle());
                    intent.putExtra("description",people_joined.getDescription());
                    intent.putExtra("tags",people_joined.getTags());
                    if (people_joined.getChallenge_title().equals("Create Campaign")){
                        intent.putExtra("type","campaign");
                    }
                    else {
                        intent.putExtra("type","challenge");
                    }
                    intent.putExtra("country",people_joined.getCountry());
                    intent.putExtra("view_count",people_joined.getView_count());
                    intent.putExtra("join","join");

                    mcontext.startActivity(intent);

                }

            }
        });



        //join visibility gone
        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
        final String uidd = preferences.getString(Constant.USER_ID,"");
        if (mchallenge_id.equals("3") || mchallenge_id.equals("2")){

            if (mjoinuser!=null && mjoinuser.equals("yes")){
                if (uidd.equals(museridd)){
                    mlinearjoin.setVisibility(View.GONE);
                }
                else if(uidd.equals(muserid1) || uidd.equals(muserid2) || uidd.equals(muserid3)){
                    mlinearjoin.setVisibility(View.GONE);
                }
                else {
                        mlinearjoin.setVisibility(View.VISIBLE);

                }
            }
            else {
                mlinearjoin.setVisibility(View.GONE);
            }
        }
        else {
            if (uidd.equals(museridd)){
                mlinearjoin.setVisibility(View.GONE);
            }
            else if (uidd.equals(muserid1) || uidd.equals(muserid2) || uidd.equals(muserid3)){
                mlinearjoin.setVisibility(View.GONE);
            }
            else {
                if(people_joined.getUser_id().equals(uidd)){
                    mlinearjoin.setVisibility(View.GONE);
                }
                else{

                        mlinearjoin.setVisibility(View.VISIBLE);
                }
            }
        }




        mjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
                final String uid = preferences.getString(Constant.USER_ID,"");

                if (mchallenge_id.equals("3") || mchallenge_id.equals("2")){

                    if (mjoinuser!=null && mjoinuser.equals("yes")){
                        if (uid.equals(museridd)){
                            Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjoinyourownchallenge));
                        }
                        else if(uid.equals(muserid1) || uid.equals(muserid2) || uid.equals(muserid3)){
                            Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.AlreadyAttempted));
                        }
                        else {
                            if (Constant.isOnline(mcontext)){


                                joinApi();

                            }
                            else{
                                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                            }

                        }
                    }
                    else {
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjointhischallenge));
                    }
                }
                else {
                    if (uid.equals(museridd)){
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Youcannotjoinyourownchallenge));
                    }
                    else if (uid.equals(muserid1) || uid.equals(muserid2) || uid.equals(muserid3)){
                        Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.AlreadyAttempted));
                    }
                    else {
                        if(people_joined.getUser_id().equals(uid)){
                            Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.AlreadyAttempted));
                        }
                        else{

                            if (Constant.isOnline(mcontext)){


                                joinApi();

                            }
                            else{
                                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                            }


                        }
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mpeople.size();

    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        RoundedImageView image;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

        }

    }

    private void joinApi(){

        mavibackground.setVisibility(View.VISIBLE);
        mavi.smoothToShow();

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,mcontext.MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");
        RequestParams params = new RequestParams();
        params.add("user_id",uid);
        params.add("challenge_id",mchallenge_id);
        params.add("accept_reject","1");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "post/post/join_challenge", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("joinres",res);

                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");

                    if (status.equals("true") && errorMessage.equals("")){

                        Intent intent = new Intent(mcontext,Camera_Activity2.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        if (mctype.equals("campaign")){
                            intent.putExtra("from","campaign");
                            intent.putExtra("join","join");
                            intent.putExtra("cid",mchallenge_id);
                        }
                        else {
                            intent.putExtra("from","challenge");
                            intent.putExtra("join","join");
                            intent.putExtra("type","selfchallenge");
                            intent.putExtra("cid",mchallenge_id);
                        }

                        if (mchallenge_title.equals("Open Challenge")){

                            intent.putExtra("type","1");

                        }
                        else if (mchallenge_title.equals("1-on-1 Challenge ")){

                            intent.putExtra("type","3");

                        }
                        else if (mchallenge_title.equals("Self Challenge")){

                            intent.putExtra("type","4");

                        }
                        else if (mchallenge_title.equals("Group Challenge")){

                            intent.putExtra("type","2");

                        }
                        else if (mchallenge_title.equals("Location Base Challenge")){

                            intent.putExtra("type","6");

                        }
                        else if (mchallenge_title.equals("Create Campaign")){

                            intent.putExtra("type","5");

                        }




                        mcontext.startActivity(intent);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("joinresF",res);
                }
                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));

            }
        });
    }


}