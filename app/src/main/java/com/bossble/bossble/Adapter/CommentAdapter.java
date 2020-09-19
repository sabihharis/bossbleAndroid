package com.bossble.bossble.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
/*
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.CameraWork.Camera_Activity2;
import com.bossble.bossble.Comments.Comments_Activity;
import com.bossble.bossble.Comments.Comments_Edit_Activity;
import com.bossble.bossble.Comments.Reply_Comments_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Details.Details_Activity;
import com.bossble.bossble.Model.Comment_Reply;
import com.bossble.bossble.Model.Comments;
import com.bossble.bossble.Model.Profile_challenges;
import com.bossble.bossble.Personal_Profile_Activity;
import com.bossble.bossble.ProfileSetup.Profile_of_others_Activity;
import com.bossble.bossble.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Fawad on 6/6/2020.
 */

//CommentAdapter


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<Comments> mnames = new ArrayList<>();
    Activity mactivity;
    String mch_id="";
    Callback callback;

    public CommentAdapter(Context mcontext, ArrayList<Comments> names,String ch_id,Activity activity,Callback callback) {
        this.mcontext = mcontext;
        mnames = names;
        mch_id = ch_id;
        mactivity = activity;
        this.callback=callback;

    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_itemlayout, viewGroup, false);
        ViewHolderForCat viewHolderForCat = new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentAdapter.ViewHolderForCat viewHolderForCat, final int i) {

        final Comments comments = mnames.get(i);

        Log.e("listsize", String.valueOf(mnames.size()));

        RequestOptions options2 = new RequestOptions();
        options2.centerCrop();
        options2.placeholder(R.drawable.user_image_placeholder);
        Glide.with(mcontext)
                .load(comments.getUserimage())
                .apply(options2)
                .into(viewHolderForCat.imgpeople);


        if (comments.getUsername().equals("null") || comments.getUsername().equals("")) {
            viewHolderForCat.txtpeoplename.setText("N/A");
        } else {
            viewHolderForCat.txtpeoplename.setText(comments.getUsername());
        }


        if (comments.getUsercomment().equals("null") || comments.getUsercomment().equals("")) {
            viewHolderForCat.txtcomment.setText("N/A");
        } else {
            viewHolderForCat.txtcomment.setText(comments.getUsercomment());
        }

        if (comments.getUserreply().equals("0")){
            viewHolderForCat.viewreplies.setVisibility(View.GONE);
        }
        else {
            viewHolderForCat.viewreplies.setText(mcontext.getResources().getString(R.string.View)+" "+comments.getUserreply()+mcontext.getResources().getString(R.string.replies));

        }



        viewHolderForCat.txtreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, Reply_Comments_Activity.class);
                intent.putExtra("id",comments.getComment_id());
                intent.putExtra("cid",comments.getChallenge_id());
                intent.putExtra("username",comments.getUsername());
                mcontext.startActivity(intent);


            }
        });



        viewHolderForCat.viewreplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mcontext, Reply_Comments_Activity.class);
                intent.putExtra("id",comments.getComment_id());
                intent.putExtra("cid",comments.getChallenge_id());
                mcontext.startActivity(intent);



            }
        });

        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String userid = preferences.getString(Constant.USER_ID,"");
        if (!userid.equals(comments.getUserid())){
            viewHolderForCat.edit.setVisibility(View.GONE);
            viewHolderForCat.delete.setVisibility(View.GONE);
        }

        viewHolderForCat.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext,Comments_Edit_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("comment","comment");
                intent.putExtra("id",comments.getComment_id());
                intent.putExtra("uid",comments.getUserid());
                intent.putExtra("comment",comments.getUsercomment());
                intent.putExtra("image",comments.getUserimage());
                intent.putExtra("challenge_id",comments.getChallenge_id());

                mcontext.startActivity(intent);


            }
        });

        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        final String u_id = pref.getString(Constant.USER_ID,"");

        viewHolderForCat.imgpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_id.equals(comments.getUserid()))
                {
                    Intent intent = new Intent(mcontext,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",comments.getUserid());
                    mcontext.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",comments.getUserid());
                    mcontext.startActivity(intent);
                }
            }
        });


        viewHolderForCat.txtpeoplename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_id.equals(comments.getUserid()))
                {
                    Intent intent = new Intent(mcontext,Personal_Profile_Activity.class);
                    intent.putExtra("user_id",comments.getUserid());
                    mcontext.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(mcontext,Profile_of_others_Activity.class);
                    intent.putExtra("user_id",comments.getUserid());
                    mcontext.startActivity(intent);
                }
            }
        });


        viewHolderForCat.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletedialog(comments.getComment_id());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mnames.size();
    }

    //for removing redundancy
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //end for removing redundancy

    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView txtpeoplename, txtcomment, txtreply,viewreplies,edit,delete;
        RoundedImageView imgpeople;
        RecyclerView recyclerreply;
        CommentReplyAdapter commentReplyAdapter;

        ArrayList<Comment_Reply> filters = new ArrayList();


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            txtpeoplename = itemView.findViewById(R.id.txtpeoplename);
            txtcomment = itemView.findViewById(R.id.txtcomment);
            txtreply = itemView.findViewById(R.id.txtreply);
            imgpeople = itemView.findViewById(R.id.imgpeople);
            recyclerreply = itemView.findViewById(R.id.recyclerreply);
            viewreplies = itemView.findViewById(R.id.viewreplies);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            txtcomment.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Regular.ttf"));
            txtpeoplename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Medium.ttf"));
            txtreply.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Regular.ttf"));
            edit.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Regular.ttf"));
            delete.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Roboto-Regular.ttf"));
            viewreplies.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Ubuntu-Medium.ttf"));

            // comments(itemView);




        }

    }

    public interface Callback{
        void onCommentDeleted(String status);
    }


    private void delete(String comment_id){



        SharedPreferences preferences = mcontext.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String usid = preferences.getString(Constant.USER_ID,"");

        RequestParams params = new RequestParams();
        params.add("comment_id",comment_id);
        params.add("user_id",usid);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Constant.BASE_URL + "comment/comment/commnet_delete", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.e("delResp",res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String errorMessage = jsonObject.getString("errorMessage");
                    if (status.equals("true") && errorMessage.equals("")){

                        callback.onCommentDeleted("true");



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                if (responseBody!=null){

                    String res = new String(responseBody);
                    Log.e("delRespF",res);

                }
                Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.Somethingwentwrong));
            }
        });



    }

    public  void deletedialog(final String cid){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mactivity);
        builder1.setMessage("Are you sure you want to delete this comment?");
        builder1.setCancelable(false);

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        builder1.setPositiveButton(
                "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (Constant.isOnline(mcontext)){

                            delete(cid);

                        }
                        else{
                            Constant.ErrorToast(mactivity,mcontext.getResources().getString(R.string.NoInternetConnection));
                        }

                    }
                });


        final AlertDialog alert11 = builder1.create();
        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mcontext.getResources().getColor(R.color.black));
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mcontext.getResources().getColor(R.color.lightfbcolor));

            }
        });


        alert11.show();

    }


}

