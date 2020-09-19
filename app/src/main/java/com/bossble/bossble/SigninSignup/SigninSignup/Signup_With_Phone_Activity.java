package com.bossble.bossble.SigninSignup.SigninSignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
/*
import android.support.v7.app.AppCompatActivity;
*/
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.ForgotPassword.Forgot_Password_Activity;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.R;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rilixtech.widget.countrycodepicker.Country;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class Signup_With_Phone_Activity extends AppCompatActivity {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mcurrentVideoPosition;

    TextView txtenter,txtphone,txtmember,txtlogin;
    EditText edphone,edpassword;
    CountryCodePicker select_code;
    Button btnverify;

    String cip="";
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String cname="";
    String code="";

    String latitute ;
    String longitude;
    com.rilixtech.widget.countrycodepicker.CountryCodePicker ccp;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup__with__phone_);
        overridePendingTransition(R.anim.anim1, R.anim.anim2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        ccp = findViewById(R.id.ccp);





        names.add("Afghanistan");
        names.add("Albania");
        names.add("Algeria");
        names.add("American Samoa");
        names.add("Andorra");
        names.add("Angola");
        names.add("Anguilla");
        names.add("Antarctica");
        names.add("Antigua");
        names.add("Argentina");
        names.add("Armenia");
        names.add("Aruba");
        names.add("Ascension");
        names.add("Australia");
        names.add("Australian External Territories");
        names.add("Austria");
        names.add("Azerbaijan");
        names.add("Bahamas");
        names.add("Bahrain");
        names.add("Bangladesh");
        names.add("Barbados");
        names.add("Barbuda");
        names.add("Belarus");
        names.add("Belgium");
        names.add("Belize");
        names.add("Benin");
        names.add("Bermuda");
        names.add("Bhutan");
        names.add("Bolivia");
        names.add("Bosnia & Herzegovina");
        names.add("Botswana");
        names.add("Brazil");
        names.add("British Virgin Islands");
        names.add("Brunei Darussalam");
        names.add("Bulgaria");
        names.add("Burkina Faso");
        names.add("Burundi");
        names.add("Cambodia");
        names.add("Cameroon");
        names.add("Canada");
        names.add("Cape Verde Islands");
        names.add("Cayman Islands");
        names.add("Central African Republic");
        names.add("Chad");
        names.add("Chatham Island (New Zealand)");
        names.add("Chile");
        names.add("China");
        names.add("Christmas Island");
        names.add("Cocos-Keeling Islands");
        names.add("Colombia");
        names.add("Comoros");
        names.add("Congo");
        names.add("Congo, Dem. Rep. of  (former Zaire)");
        names.add("Cook Islands");
        names.add("Costa Rica");
        names.add("Côte d'Ivoire (Ivory Coast)");
        names.add("Croatia");
        names.add("Cuba");
        names.add("Cuba (Guantanamo Bay)");
        names.add("Curaçao");
        names.add("Cyprus");
        names.add("Czech Republic");
        names.add("Denmark");
        names.add("Diego Garcia");
        names.add("Djibouti");
        names.add("Dominica");
        names.add("Dominican Republic");
        names.add("East Timor");
        names.add("Easter Island");
        names.add("Ecuador");
        names.add("Egypt");
        names.add("El Salvador");
        names.add("Equatorial Guinea");
        names.add("Eritrea");
        names.add("Estonia");
        names.add("Ethiopia");
        names.add("Falkland Islands (Malvinas)");
        names.add("Faroe Islands");
        names.add("Fiji Islands");
        names.add("Finland");
        names.add("France");
        names.add("French Antilles");
        names.add("French Guiana");
        names.add("French Polynesia");
        names.add("Gabonese Republic");
        names.add("Gambia");
        names.add("Georgia");
        names.add("Germany");
        names.add("Ghana");
        names.add("Gibraltar");
        names.add("Greece");
        names.add("Greenland");
        names.add("Grenada");
        names.add("Guadeloupe");
        names.add("Guam");
        names.add("Guantanamo Bay");
        names.add("Guatemala");
        names.add("Guinea-Bissau");
        names.add("Guinea");
        names.add("Guyana");
        names.add("Haiti");
        names.add("Honduras");
        names.add("Hong Kong");
        names.add("Hungary");
        names.add("Iceland");
        names.add("India");
        names.add("Indonesia");
        names.add("Iran");
        names.add("Iraq");
        names.add("Ireland");
        names.add("Israel");
        names.add("Italy");
        names.add("Jamaica");
        names.add("Japan");
        names.add("Jordan");
        names.add("Kazakhstan");
        names.add("Kenya");
        names.add("Kiribati");
        names.add("Korea (North)");
        names.add("Korea (South)");
        names.add("Kuwait");
        names.add("Kyrgyz Republic");
        names.add("Laos");
        names.add("Latvia");
        names.add("Lebanon");
        names.add("Lesotho");
        names.add("Liberia");
        names.add("Libya");
        names.add("Liechtenstein");
        names.add("Lithuania");
        names.add("Luxembourg");
        names.add("Macao");
        names.add("Macedonia (Former Yugoslav Rep of.)");
        names.add("Madagascar");
        names.add("Malawi");
        names.add("Malaysia");
        names.add("Maldives");
        names.add("Mali Republic");
        names.add("Malta");
        names.add("Marshall Islands");
        names.add("Martinique");
        names.add("Mauritania");
        names.add("Mauritius");
        names.add("Mayotte Island");
        names.add("Mexico");
        names.add("Micronesia, (Federal States of)");
        names.add("Midway Island");
        names.add("Moldova");
        names.add("Monaco");
        names.add("Mongolia");
        names.add("Montenegro");
        names.add("Montserrat");
        names.add("Morocco");
        names.add("Mozambique");
        names.add("Myanmar");
        names.add("Namibia");
        names.add("Nauru");
        names.add("Nepal");
        names.add("Netherlands");
        names.add("Netherlands Antilles");
        names.add("Nevis");
        names.add("New Caledonia");
        names.add("New Zealand");
        names.add("Nicaragua");
        names.add("Niger");
        names.add("Nigeria");
        names.add("Niue");
        names.add("Norfolk Island");
        names.add("Northern Marianas Islands (Saipan, Rota, & Tinian)");
        names.add("Oman");
        names.add("Pakistan");
        names.add("Palau");
        names.add("Palestinian Settlements");
        names.add("Panama");
        names.add("Papua New Guinea");
        names.add("Paraguay");
        names.add("Peru");
        names.add("Philippines");
        names.add("Poland");
        names.add("Portugal");
        names.add("Puerto Rico");
        names.add("Qatar");
        names.add("Réunion Island");
        names.add("Romania");
        names.add("Russia");
        names.add("Rwandese Republic");
        names.add("St. Helena");
        names.add("St. Kitts/Nevis");
        names.add("St. Lucia");
        names.add("St. Pierre & Miquelon");
        names.add("St. Vincent & Grenadines");
        names.add("Samoa");
        names.add("San Marino");
        names.add("São Tomé and Principe");
        names.add("Saudi Arabia");
        names.add("Senegal");
        names.add("Serbia");
        names.add("Seychelles Republic");
        names.add("Sierra Leone");
        names.add("Singapore");
        names.add("Slovak Republic");
        names.add("Slovenia");
        names.add("Solomon Islands");
        names.add("Somali Democratic Republic");
        names.add("South Africa");
        names.add("Spain");
        names.add("Sri Lanka");
        names.add("Sudan");
        names.add("Suriname");
        names.add("Swaziland");
        names.add("Sweden");
        names.add("Switzerland");
        names.add("Syria");
        names.add("Taiwan");
        names.add("Tajikistan");
        names.add("Tanzania");
        names.add("Thailand");
        names.add("Timor Leste");
        names.add("Togolese Republic");
        names.add("Tokelau");
        names.add("Tonga Islands");
        names.add("Trinidad & Tobago");
        names.add("Tunisia");
        names.add("Turkey");
        names.add("Turkmenistan");
        names.add("Turks and Caicos Islands");
        names.add("Tuvalu");
        names.add("Uganda");
        names.add("Ukraine");
        names.add("United Arab Emirates");
        names.add("United Kingdom");
        names.add("United States of America");
        names.add("US Virgin Islands");
        names.add("Uruguay");
        names.add("Uzbekistan");
        names.add("Vanuatu");
        names.add("Venezuela");
        names.add("Vietnam");
        names.add("Wake Island");
        names.add("Wallis and Futuna Islands");
        names.add("Yemen");
        names.add("Zambia");
        names.add("Zanzibar");
        names.add("Zimbabwe");




        codes.add("93");
        codes.add("355");
        codes.add("213");
        codes.add("1684");
        codes.add("376");
        codes.add("244");
        codes.add("1264");
        codes.add("672");
        codes.add("1268");
        codes.add("54");
        codes.add("374");
        codes.add("297");
        codes.add("247");
        codes.add("61");
        codes.add("672");
        codes.add("43");
        codes.add("994");
        codes.add("1242");
        codes.add("973");
        codes.add("880");
        codes.add("1246");
        codes.add("1268");
        codes.add("375");
        codes.add("32");
        codes.add("501");
        codes.add("229");
        codes.add("1441");
        codes.add("975");
        codes.add("591");
        codes.add("387");
        codes.add("267");
        codes.add("55");
        codes.add("1284");
        codes.add("673");
        codes.add("359");
        codes.add("226");
        codes.add("257");
        codes.add("855");
        codes.add("237");
        codes.add("1");
        codes.add("238");
        codes.add("1345");
        codes.add("236");
        codes.add("235");
        codes.add("64");
        codes.add("56");
        codes.add("86");
        codes.add("61");
        codes.add("61");
        codes.add("57");
        codes.add("269");
        codes.add("242");
        codes.add("243");
        codes.add("682");
        codes.add("506");
        codes.add("225");
        codes.add("385");
        codes.add("53");
        codes.add("5399");
        codes.add("5999");
        codes.add("357");
        codes.add("420");
        codes.add("45");
        codes.add("246");
        codes.add("253");
        codes.add("1767");
        codes.add("1809");
        codes.add("670");
        codes.add("56");
        codes.add("593");
        codes.add("20");
        codes.add("503");
        codes.add("240");
        codes.add("291");
        codes.add("372");
        codes.add("251");
        codes.add("500");
        codes.add("298");
        codes.add("679");
        codes.add("358");
        codes.add("33");
        codes.add("596");
        codes.add("594");
        codes.add("689");
        codes.add("241");
        codes.add("220");
        codes.add("995");
        codes.add("49");
        codes.add("233");
        codes.add("350");
        codes.add("30");
        codes.add("299");
        codes.add("1473");
        codes.add("590");
        codes.add("1671");
        codes.add("5399");
        codes.add("502");
        codes.add("245");
        codes.add("224");
        codes.add("592");
        codes.add("509");
        codes.add("504");
        codes.add("852");
        codes.add("36");
        codes.add("354");
        codes.add("91");
        codes.add("62");
        codes.add("98");
        codes.add("964");
        codes.add("353");
        codes.add("972");
        codes.add("39");
        codes.add("1876");
        codes.add("81");
        codes.add("962");
        codes.add("7");
        codes.add("254");
        codes.add("686");
        codes.add("850");
        codes.add("82");
        codes.add("965");
        codes.add("996");
        codes.add("856");
        codes.add("371");
        codes.add("961");
        codes.add("266");
        codes.add("231");
        codes.add("218");
        codes.add("423");
        codes.add("370");
        codes.add("352");
        codes.add("853");
        codes.add("389");
        codes.add("261");
        codes.add("265");
        codes.add("60");
        codes.add("960");
        codes.add("223");
        codes.add("356");
        codes.add("692");
        codes.add("596");
        codes.add("222");
        codes.add("230");
        codes.add("269");
        codes.add("52");
        codes.add("691");
        codes.add("1808");
        codes.add("373");
        codes.add("377");
        codes.add("976");
        codes.add("382");
        codes.add("1664");
        codes.add("212");
        codes.add("258");
        codes.add("95");
        codes.add("264");
        codes.add("674");
        codes.add("977");
        codes.add("31");
        codes.add("599");
        codes.add("1869");
        codes.add("687");
        codes.add("64");
        codes.add("505");
        codes.add("227");
        codes.add("234");
        codes.add("683");
        codes.add("672");
        codes.add("1670");
        codes.add("968");
        codes.add("92");
        codes.add("680");
        codes.add("970");
        codes.add("507");
        codes.add("675");
        codes.add("595");
        codes.add("51");
        codes.add("63");
        codes.add("48");
        codes.add("351");
        codes.add("1787");
        codes.add("974");
        codes.add("262");
        codes.add("40");
        codes.add("7");
        codes.add("250");
        codes.add("290");
        codes.add("1869");
        codes.add("1758");
        codes.add("508");
        codes.add("1784");
        codes.add("685");
        codes.add("378");
        codes.add("239");
        codes.add("966");
        codes.add("221");
        codes.add("381");
        codes.add("248");
        codes.add("232");
        codes.add("65");
        codes.add("421");
        codes.add("386");
        codes.add("677");
        codes.add("252");
        codes.add("27");
        codes.add("34");
        codes.add("94");
        codes.add("249");
        codes.add("597");
        codes.add("268");
        codes.add("46");
        codes.add("41");
        codes.add("963");
        codes.add("886");
        codes.add("992");
        codes.add("255");
        codes.add("66");
        codes.add("670");
        codes.add("228");
        codes.add("690");
        codes.add("676");
        codes.add("1868");
        codes.add("216");
        codes.add("90");
        codes.add("993");
        codes.add("1649");
        codes.add("688");
        codes.add("256");
        codes.add("380");
        codes.add("971");
        codes.add("44");
        codes.add("1");
        codes.add("1340");
        codes.add("598");
        codes.add("998");
        codes.add("678");
        codes.add("58");
        codes.add("84");
        codes.add("808");
        codes.add("681");
        codes.add("967");
        codes.add("260");
        codes.add("255");
        codes.add("263");

























        txtenter = findViewById(R.id.txtenter);
        txtphone = findViewById(R.id.txtphone);
        txtmember = findViewById(R.id.txtmember);
        txtlogin = findViewById(R.id.txtlogin);
        edphone = findViewById(R.id.edphone);
        edpassword = findViewById(R.id.edpassword);
        select_code = findViewById(R.id.select_code);
        btnverify = findViewById(R.id.btnverify);
        videoBG = findViewById(R.id.videoView);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        code = select_code.getDefaultCountryCode();
        cname = select_code.getDefaultCountryName();

        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        pref.edit().putString(Constant.CODE,code).commit();
        pref.edit().putString(Constant.COUNTRY,cname).commit();




        SamLocationRequestService samLocationRequestService = new SamLocationRequestService(Signup_With_Phone_Activity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                Log.e("Location", String.valueOf(location));

                 latitute = String.valueOf(location.getLatitude());
                 longitude = String.valueOf(location.getLongitude());
                Log.e("lat",latitute);
                Log.e("long",longitude);
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);

                    String add = "";
                    if (addresses.size() > 0)
                    {
                        Log.e("countryname1",addresses.get(0).getCountryName());
                    }
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        },10);


        select_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                code = select_code.getSelectedCountryCode();
                cname = select_code.getSelectedCountryName();
                pref.edit().putString(Constant.CODE,code).commit();
                pref.edit().putString(Constant.COUNTRY,cname).commit();

            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                signup_with_phonw();
            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_With_Phone_Activity.this,Signin_Activity.class);
                intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
                startActivity(intent);
            }
        });


/*
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        Log.e("countryCodeValue",countryCodeValue);
       WifiManager ip = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

      String cip = android.text.format.Formatter.formatIpAddress(ip.getConnectionInfo().getIpAddress());
        Log.e("cip",cip);
*/

        fonts();

        Uri uri = Uri.parse("android.resource://"
                +getPackageName()
                +"/"
                +R.raw.vid202005080004

        );

        videoBG.setVideoURI(uri);

        videoBG.start();

        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);

                if (mcurrentVideoPosition !=0)
                {
                    mMediaPlayer.seekTo(mcurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });


    }
    @Override
    protected void onPause() {
        super.onPause();

        mcurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  videoBG.seekTo(stopPosition);
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    public void fonts()
    {
        txtenter.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtphone.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        txtmember.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Light.ttf"));
        txtlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Bold.ttf"));
        edphone.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        btnverify.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Bold.ttf"));
    }

    public void signup_with_phonw()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);

        final String phone = edphone.getText().toString();

        final String phone_number = "+"+code+phone;

        String pass = edpassword.getText().toString();
        if (phone.isEmpty())
        {
            edphone.setError("Field is required");
            edphone.requestFocus();
        }
        else if (phone.length()<10)
        {
            edphone.setError("Atleast 10 digits required");
            edphone.requestFocus();
        }
        else if (pass.isEmpty())
        {
            edpassword.setError("Field is required");
            edpassword.requestFocus();
        }
        else{
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("register_ip",cip);
            params.add("latitude",latitute);
            params.add("longitude",longitude);
            params.add("device_type","android");
            params.add("device_id","ewewewq");
            params.add("device_token","fdfdfdfdfdfdfd");
            params.add("mobile",phone_number);
            params.add("signup_flag","1");
            params.add("password",pass);

            Log.e("l_params", String.valueOf(params));

            AsyncHttpClient client = new AsyncHttpClient();
            client.post(Constant.BASE_URL+"register_member",params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("signupresponse",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String errorMessage = jsonObject.getString("errorMessage");
                        if (status.equals("true"))
                        {
                            // Constant.SuccessToast(Signup_With_Phone_Activity.this,errorMessage);
                            JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                            String user_id = dataSet.getString("user_id");
                            String sms_code = dataSet.getString("sms_code");
                            Log.e("sms_code",sms_code);
                            pref.edit().putString(Constant.USER_ID,user_id).commit();
                            pref.edit().putString(Constant.CODE,code).commit();
                            pref.edit().putString(Constant.PHONE,phone).commit();
                            pref.edit().putString(Constant.COUNTRY,cname).commit();

                            Intent intent = new Intent(Signup_With_Phone_Activity.this,Verification_code_Activity.class);
                            intent.putExtra("ssms_code",sms_code);
                            intent.putExtra("phone",phone_number);
                            intent.putExtra("from","2");
                            startActivity(intent);


                        }
                        else if (status.equals("false"))
                        {
                            Constant.ErrorToast(Signup_With_Phone_Activity.this,jsonObject.getString("errorMessage"));
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    if (responseBody!=null){
                        String response = new String(responseBody);
                        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_SHORT).show();
                        Log.e("signupresponseF",response);

                    }

                    /*try {
                        JSONObject jsonobject = new JSONObject(response);
                        String status = jsonobject.getString("status");
                        String errorMessage = jsonobject.getString("errorMessage");

                        if (status.equals("false")) {
                            Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup_With_Phone_Activity.this,Signup_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("currentposition",String.valueOf(mcurrentVideoPosition+2000));
        startActivity(intent);
    }


    }

