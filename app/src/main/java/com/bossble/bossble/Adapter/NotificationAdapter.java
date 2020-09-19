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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.ImagePreview_Activity;
import com.bossble.bossble.Comments.Comments_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Notifications;
import com.bossble.bossble.Model.Trending_Challenges;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolderForCat> {

    private Context mcontext ;
    List<Notifications> mnames=new ArrayList<>();
    AVLoadingIndicatorView mavi;
    ImageView mavibackground;
    Activity mactivity;

    public NotificationAdapter(Context mcontext , ArrayList<Notifications> names,AVLoadingIndicatorView avi, ImageView avibackground,Activity activity){
        this.mcontext = mcontext;
        mnames=names;
        mavi = avi;
        mavibackground = avibackground;
        mactivity = activity;
    }


    @NonNull
    @Override
    public NotificationAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final Notifications notifications = mnames.get(i);
        viewHolderForCat.msg.setText(notifications.getNotification());

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(notifications.getImage())
                .apply(options)
                .into(viewHolderForCat.image);


        if (notifications.getIs_readed().equals("0")){
            viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.lightblue_background));
        }
        else {
            viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.white_background));
        }

        viewHolderForCat.time.setText(parseDateToddMMyyyy(notifications.getCreate_date()));


        viewHolderForCat.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolderForCat.relative.getBackground().getConstantState()== mcontext.getResources().getDrawable(R.drawable.lightblue_background).getConstantState()){

                    viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.white_background));

                    viewHolderForCat.linear.setVisibility(View.GONE);

                }
                else {
                    viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.lightblue_background));
                    viewHolderForCat.linear.setVisibility(View.VISIBLE);

                }

            }
        });


        viewHolderForCat.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.white_background));

                viewHolderForCat.linear.setVisibility(View.GONE);

            }
        });
        viewHolderForCat.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolderForCat.relative.setBackground(mcontext.getResources().getDrawable(R.drawable.white_background));

                viewHolderForCat.linear.setVisibility(View.GONE);

            }
        });


        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.isOnline(mcontext)){

                    if (notifications.getIs_readed().equals("0")){

                        readnotification(notifications.getNotification_id(),i,notifications);

                    }
                    else {

                        if(notifications.getNotification_type().equals("1")){


                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("2")){

                            //admire
                            Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("user_id",notifications.getUser_id());
                            mcontext.startActivity(intent);
                        }
                        else if(notifications.getNotification_type().equals("3")){

                            //unadmire
                            Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("user_id",notifications.getUser_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("4")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("6")){

                            Intent intent = new Intent(mcontext,Comments_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("challenge_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("8")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);
                        }
                        else if(notifications.getNotification_type().equals("12")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("14")){

                            //a follower has challenged you  (join)
                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("accept","accept");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("15")){
                            //a follower has challenged you  (join)
                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("accept","accept");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }


                    }
                }
                else{
                    Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                }



            }
        });

    }

    @Override
    public int getItemCount() {

        return mnames.size();

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public class ViewHolderForCat extends RecyclerView.ViewHolder {



        RoundedImageView image;
        TextView msg,time;
        LinearLayout linear;
        ImageView accept,reject;
        RelativeLayout relative;

        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            msg = itemView.findViewById(R.id.msg);
            time = itemView.findViewById(R.id.time);
            linear = itemView.findViewById(R.id.linear);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
            relative = itemView.findViewById(R.id.relative);


            msg.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));
            time.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Roboto-Light.ttf"));

        }

    }

    private void updateNotification(final int adapterPosition, Notifications notifications, String status) {


        if (status.equals("true")){

            notifications.setIs_readed("1");
            notifyItemChanged(adapterPosition, "prelike");

        }

    }

    private void readnotification(String notificationid, final int pos, final Notifications notifications){

        mavibackground.setVisibility(View.VISIBLE);
        mavi.smoothToShow();

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
        String uid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("user_id",uid);
        params.add("notification_id",notificationid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "notification/notification/read_notification", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("res",res);

                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){


                        updateNotification(pos,notifications,"true");

                        if(notifications.getNotification_type().equals("1")){


                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("2")){

                            //admire
                            Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("user_id",notifications.getUser_id());
                            mcontext.startActivity(intent);
                        }
                        else if(notifications.getNotification_type().equals("3")){

                            //unadmire
                            Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("user_id",notifications.getUser_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("4")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("6")){

                            Intent intent = new Intent(mcontext,Comments_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("challenge_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("7")){

                            Intent intent = new Intent(mcontext,Comments_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("challenge_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("8")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);
                        }
                        else if(notifications.getNotification_type().equals("12")){

                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("14")){

                            //a follower has challenged you  (join)
                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("accept","accept");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }
                        else if(notifications.getNotification_type().equals("15")){
                            //a follower has challenged you  (join)
                            Intent intent = new Intent(mcontext,Details_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("notifications","notifications");
                            intent.putExtra("accept","accept");
                            intent.putExtra("np_id",notifications.getPost_id());
                            mcontext.startActivity(intent);

                        }



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){
                    String res = new String(responseBody);
                    Log.e("resF",res);
                }
                mavibackground.setVisibility(View.GONE);
                mavi.smoothToHide();
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));

            }
        });

    }
}