package com.bossble.bossble.Maps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
/*
import androidx.core.app.FragmentActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.R;
import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double latitute;
    double longitude;
    String lati="";
    String longi="";
    String countryname="";
    TextView done;
    String addressline="";
    ImageView mark;
    SearchView search;
    ImageView mylocation;
    String radius="0";
    String country_city="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        initialize_places();
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.e("googlePlaces","Place: " + place.getLatLng());

                if (place != null) {
                    LatLng latLng = place.getLatLng();
                    String mStringLatitude = String.valueOf(latLng.latitude);
                    String mStringLongitude = String.valueOf(latLng.longitude);
                    Log.e("googlePlacesLat",mStringLatitude);
                    Log.e("googlePlacesLong",mStringLongitude);
                    Log.e("googlePlacesName",place.getAddress());


                    LatLng latLng2 = new LatLng(Double.parseDouble(mStringLatitude), Double.parseDouble(mStringLongitude));
                    addressline = place.getAddress();
                    mMap.addMarker(new MarkerOptions().position(latLng2).title(addressline).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)).draggable(false));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( latLng2, 15.0f));

                    lati=String.valueOf(latLng2.latitude);
                    longi=String.valueOf(latLng2.longitude);


                }
            }

            @Override
            public void onError(Status status) {
                Log.e("googlePlacesError", String.valueOf(status));
                Constant.ErrorToast(MapsActivity.this,"Unable to fetch location");

            }
        });


        done = findViewById(R.id.done);
        mark = findViewById(R.id.mark);
        search = findViewById(R.id.search);
        mylocation = findViewById(R.id.mylocation);




        search.setFocusable(false);

        mapFragment.getMapAsync(this);




        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (lati.equals("") || lati.equals("0.0") || longi.equals("") || longi.equals("0.0")){
                    SamLocationRequestService samLocationRequestService = new SamLocationRequestService(MapsActivity.this, new SamLocationRequestService.SamLocationListener() {
                        @Override
                        public void onLocationUpdate(Location location, Address address) {

                            Log.e("Location", String.valueOf(location));
                            latitute = location.getLatitude();
                            longitude = location.getLongitude();


                            Log.e("lat", String.valueOf(latitute));
                            Log.e("long", String.valueOf(longitude));
                            Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                            try {
                                List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);
                                String add = "";
                                if (addresses.size() > 0)
                                {
                                    LatLng sydney = new LatLng(latitute,longitude);
                                    addressline = addresses.get(0).getAddressLine(0);
                                    mMap.addMarker(new MarkerOptions().position(sydney).title(addressline).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)).draggable(false));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( sydney, 15.0f));

                                    lati=String.valueOf(sydney.latitude);
                                    longi=String.valueOf(sydney.longitude);



                                }
                            }
                            catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    },10);
                }
                else {
                    radiusdialog();
                }
            }
        });


        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SamLocationRequestService samLocationRequestService = new SamLocationRequestService(MapsActivity.this, new SamLocationRequestService.SamLocationListener() {
                    @Override
                    public void onLocationUpdate(Location location, Address address) {

                        Log.e("Location", String.valueOf(location));
                        latitute = location.getLatitude();
                        longitude = location.getLongitude();


                        Log.e("lat", String.valueOf(latitute));
                        Log.e("long", String.valueOf(longitude));
                        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);
                            String add = "";
                            if (addresses.size() > 0)
                            {
                                LatLng sydney = new LatLng(latitute,longitude);
                                addressline = addresses.get(0).getAddressLine(0);
                                mMap.addMarker(new MarkerOptions().position(sydney).title(addressline).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)).draggable(false));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( sydney, 15.0f));

                                lati=String.valueOf(sydney.latitude);
                                longi=String.valueOf(sydney.longitude);



                            }
                        }
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                },10);
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = search.getQuery().toString();
                List<Address> addressList = null;
                if (location!=null || !location.equals("")){
                    Geocoder geoCoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geoCoder.getFromLocationName(location, 1);

                        String add = "";
                        if (addressList.size() > 0)
                        {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            addressline = addressList.get(0).getAddressLine(0);
                            mMap.addMarker(new MarkerOptions().position(latLng).title(addressline).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)).draggable(false));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( latLng, 15.0f));

                            lati=String.valueOf(latLng.latitude);
                            longi=String.valueOf(latLng.longitude);




                        }
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        mMap = googleMap;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        SamLocationRequestService samLocationRequestService = new SamLocationRequestService(MapsActivity.this, new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {

                Log.e("Location", String.valueOf(location));
                latitute = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(latitute), Double.valueOf(longitude), 1);

                    String add = "";
                    if (addresses.size() > 0)
                    {

                        LatLng sydney = new LatLng(latitute,longitude);
                        addressline = addresses.get(0).getAddressLine(0);
                        mMap.addMarker(new MarkerOptions().position(sydney).title(addressline).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)).draggable(false));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( sydney, 15.0f));

                    }
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        },10);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng pos = marker.getPosition();
                lati=String.valueOf(pos.latitude);
                longi=String.valueOf(pos.longitude);
                Log.e("draggedlat", String.valueOf(lati));
                Log.e("draggedlong", String.valueOf(longi));


                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(lati), Double.valueOf(longi), 1);

                    if (addresses.size() > 0)
                    {
                        Log.e("addressline", addresses.get(0).getAddressLine(0));
                        addressline = addresses.get(0).getAddressLine(0);

                    }
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                mark.setVisibility(View.GONE);
                LatLng midLatLng = mMap.getCameraPosition().target;
                mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_mark)));
                lati=String.valueOf(midLatLng.latitude);
                longi=String.valueOf(midLatLng.longitude);

                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(Double.valueOf(lati), Double.valueOf(longi), 1);

                    String add = "";
                    if (addresses.size() > 0)
                    {

                        addressline = addresses.get(0).getAddressLine(0);

                    }
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }




            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mMap.clear();
                mark.setVisibility(View.VISIBLE);
                dismissKeyboard(MapsActivity.this);

            }
        });


    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }


    private void radiusdialog(){

        final Dialog mybuilder=new Dialog(MapsActivity.this);
        mybuilder.setContentView(R.layout.map_radius_dialog);

        final TextView zero,one,five,ten,twenty,ok;

        zero = mybuilder.findViewById(R.id.zero);
        one = mybuilder.findViewById(R.id.one);
        five = mybuilder.findViewById(R.id.five);
        ten = mybuilder.findViewById(R.id.ten);
        twenty = mybuilder.findViewById(R.id.twenty);
        ok = mybuilder.findViewById(R.id.ok);

        zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
        ok.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Medium.ttf"));
        one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
        twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));


        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zero.setTextSize(18);
                one.setTextSize(13);
                five.setTextSize(13);
                ten.setTextSize(13);
                twenty.setTextSize(13);

                zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
                one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

                radius="0";



            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one.setTextSize(18);
                zero.setTextSize(13);
                five.setTextSize(13);
                ten.setTextSize(13);
                twenty.setTextSize(13);

                one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
                zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

                radius="1";

            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                five.setTextSize(18);
                one.setTextSize(13);
                zero.setTextSize(13);
                ten.setTextSize(13);
                twenty.setTextSize(13);

                five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
                one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

                radius="5";

            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ten.setTextSize(18);
                one.setTextSize(13);
                five.setTextSize(13);
                zero.setTextSize(13);
                twenty.setTextSize(13);

                ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
                one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

                radius="10";

            }
        });

        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twenty.setTextSize(18);
                one.setTextSize(13);
                five.setTextSize(13);
                ten.setTextSize(13);
                zero.setTextSize(13);

                twenty.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
                one.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                five.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                ten.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));
                zero.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Roboto-Regular.ttf"));

                radius="20";


            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybuilder.dismiss();


                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                if (!lati.equals("") && !longi.equals("")){

                    if(radius.equals("0")){

                        preferences.edit().putString("lat",lati).commit();
                        preferences.edit().putString("long",longi).commit();
                        preferences.edit().putString("address",addressline).commit();
                        preferences.edit().putString("radius",radius).commit();

                    }
                    else {
                        try {
                            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            addresses = geocoder.getFromLocation(Double.valueOf(lati), Double.valueOf(longi), 1);
                            String cityName = addresses.get(0).getLocality();
                            String countryName = addresses.get(0).getCountryName();

                            country_city = countryName+", "+cityName;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        preferences.edit().putString("lat",lati).commit();
                        preferences.edit().putString("long",longi).commit();
                        preferences.edit().putString("address",country_city).commit();
                        preferences.edit().putString("radius",radius).commit();

                    }
                }
                else {

                    if(radius.equals("0")){

                        preferences.edit().putString("lat", String.valueOf(latitute)).commit();
                        preferences.edit().putString("long", String.valueOf(longitude)).commit();
                        preferences.edit().putString("address",addressline).commit();
                        preferences.edit().putString("radius",radius).commit();

                    }
                    else {
                        try {
                            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            addresses = geocoder.getFromLocation(latitute, longitude, 1);
                            String cityName = addresses.get(0).getLocality();
                            String countryName = addresses.get(0).getCountryName();

                            country_city = countryName+", "+cityName;


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        preferences.edit().putString("lat", String.valueOf(latitute)).commit();
                        preferences.edit().putString("long", String.valueOf(longitude)).commit();
                        preferences.edit().putString("address",country_city).commit();
                        preferences.edit().putString("radius",radius).commit();

                    }
                }

                MapsActivity.this.onBackPressed();

            }
        });



        mybuilder.show();
        mybuilder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window window = mybuilder.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);



    }

    private void initialize_places(){
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), Constant.API_KEY_PART_1+Constant.API_KEY_PART_2+Constant.API_KEY_PART_3+Constant.API_KEY_PART_4);

        }

    }


}
