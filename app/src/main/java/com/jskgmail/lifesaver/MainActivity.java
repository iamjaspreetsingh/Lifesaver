package com.jskgmail.lifesaver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jskgmail.lifesaver.beaconreference.BeaconTransmitterActivity;
import com.jskgmail.lifesaver.beaconreference.BeaconTransmitterActivity1;
import com.jskgmail.lifesaver.beaconreference.MonitoringActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, SensorEventListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
    {
    ;
    Uri photoURI;

    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    private SliderLayout mDemoSlider;
    private BoomMenuButton bmb;
    private static final int RESULT_PICK_CONTACT = 85;
    private ArrayList<String> stringArrayList, stringArrayList1;
    static String blba="No BloodBank Found",blba1="N.A.",blba11="N.A.";
    ListViewAdfrlist adapter;
    static String phon="",naam="";static String myLocation;
    static double mylocationa;
    static double myLocationb;int ch=0;
    static String bloodloc;
    public static String hospname="";
        public static String bbname="";
    static String ZIP="N.A.";
   static String hosp="";
    public static String bloodno="";
    String TAG = "taggg";
    static double lat=39.7,longi=-104;
    static String hospp="No Hospital Found",hospp1="N.A.",hospp11="N.A.";
    public static String latlong = "";//the realtime latitude longitude parameter
    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";
    private final static String API_KEY1 = "AIzaSyCGZpTkUUlIYjYuJNOZMJKA6Ar4d7fE7Dc";
    double eleva = 0;
    static String latitude="28.620660,77.08127";
    public static double diffelevation = 0;
    File image = null;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    String mCurrentPhotoPath;
    private Sensor senAccelerometer;
    private SensorManager senSensorManager;
    float lastx=0,lasty=0,lastz=0;
    long lastupdate = System.currentTimeMillis();
    public static String emergencyno="";
    public static String flood="0";
ListView l;
RelativeLayout rl;

    private SensorManager mSensorManager;
    private Sensor TemperatureSensor;
    private String API_KEYpin="AIzaSyBCy3Ghs09Bk0YULL2SmI-F5yXTJ6KJCWg";
    File photoFile = null;
    String responseddd;
        private StorageReference mStorageRef;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

rl=findViewById(R.id.rla);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        TemperatureSensor = senSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        senSensorManager.registerListener(this,
                TemperatureSensor,
                SensorManager.SENSOR_DELAY_NORMAL);


stringArrayList=new ArrayList<>();

        stringArrayList1=new ArrayList<>();


































        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
        String bal = prefs.getString("balance", "N.A.");

        View header=navigationView.getHeaderView(0) ;
          TextView textView=header.findViewById(R.id.text);
          textView.setText("BALANCE: "+bal);

        navigationView.setNavigationItemSelectedListener(this);

        accountdetail();

        startService();

        //my_app_starter();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        int RC_LOCATION = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);

        String[] perms = { android.Manifest.permission.ACCESS_FINE_LOCATION,  };
        if(RC_LOCATION!=0)
            if (EasyPermissions.somePermissionPermanentlyDenied(this, Arrays.asList(perms))) {
                new AppSettingsDialog.Builder(this).build().show();
            }





















        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_3);
        Log.e("ppp", ""+bmb.getPiecePlaceEnum().pieceNumber());

        final Uri[] call = new Uri[1];
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index==0)
                                call[0] =Uri.parse("tel:"+"102");
                            else if (index==1)call[0] =Uri.parse("tel:"+"100");
                            else if (index==2)call[0] =Uri.parse("tel:"+"101");
                            else if (index==3)call[0] =Uri.parse("tel:"+"181");
                            else if (index==4)call[0] =Uri.parse("tel:"+"108");
                            else if (index==5) call[0] =Uri.parse("tel:"+"1098");

                            Intent surf=new Intent(Intent.ACTION_DIAL, call[0]);
                            startActivity(surf);

                        }
                    })

                    .normalImageRes(BuilderManager.getImageResource())
                    .normalText(BuilderManager.getImageText())
                    .pieceColor(Color.WHITE).normalColor(Color.WHITE);


            bmb.addBuilder(builder);
        }





        mDemoSlider = (SliderLayout)findViewById(R.id.slider);



        mViewPager = (ViewPager) findViewById(R.id.viewPager);



        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_2));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_3));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_4));
        mCardAdapter.addCardItem(new CardItem(R.string.title_5, R.string.text_5));
        mCardAdapter.addCardItem(new CardItem(R.string.title_6, R.string.text_6));
        mCardAdapter.addCardItem(new CardItem(R.string.title_7, R.string.text_7));
        mCardAdapter.addCardItem(new CardItem(R.string.title_8, R.string.text_8));
        mCardAdapter.addCardItem(new CardItem(R.string.title_9, R.string.text_9));

        Log.e( "clclclclcl", String.valueOf(mViewPager.getCurrentItem()));
        mViewPager.setClickable(true);


        Log.e("clcl", String.valueOf(mViewPager.hasOnClickListeners()));
        mViewPager.setAdapter(mCardAdapter);

        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);















        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Safety Tips: Earthquake",R.mipmap.earthquake);
        file_maps.put("Safety Tips: Flood",R.mipmap.flood);
        file_maps.put("Safety Tips: Accidents",R.drawable.acc);
        file_maps.put("Safety Tips: Fire", R.drawable.fire);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        LinearLayout ho=findViewById(R.id.ho);
        LinearLayout bb=findViewById(R.id.bb);
        LinearLayout ps=findViewById(R.id.ps);

        LinearLayout rv=findViewById(R.id.posss);
        LinearLayout cr=findViewById(R.id.possn);
        ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MainhospActivity.class);
                startActivity(i);
            }
        });
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MainbbActivity.class);
                startActivity(i);
            }
        });
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MainppActivity.class);
                startActivity(i);
            }
        });

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Webvan.class);
                startActivity(i);

            }
        });
cr.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(MainActivity.this,Webcrane.class);
        startActivity(i);

    }
});
        LinearLayout comp=findViewById(R.id.hoo);
        LinearLayout viewc=findViewById(R.id.imageView7);
        LinearLayout bea=findViewById(R.id.bob);
        LinearLayout img=findViewById(R.id.poss);
        LinearLayout emer=findViewById(R.id.pos);
viewc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(MainActivity.this,MapsActivity.class);
        startActivity(i);
    }
});
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ProblemActivity.class);
                startActivity(i);
            }
        });

        bea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MonitoringActivity.class);
                startActivity(i);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int CAMERA_REQUEST = 50;

                boolean isEnabled = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED;



                if (isEnabled)
                    dispatchTakePictureIntent();
                else
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.CAMERA}, CAMERA_REQUEST);


            }
        });
        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(i);
            }

        });

























    }






    @Override
    public void onConnected(Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {

            startLocationUpdates1();



        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();

            latlong=latitude+","+longitude;
            lat=latitude;
            longi=longitude;

            goapii();
            goapipin();
            go();
            //startService();
            startLocationUpdates();

        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }









    public void startService() {
        startService(new Intent(getBaseContext(), MyService.class));



    }
















    protected void startLocationUpdates1() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100000)
                .setFastestInterval(10000);
        // Request location updates

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.










            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
        Log.d("reque", "--->>>>");


        finish();






    }











































































    protected void startLocationUpdates() {
        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
        String myno="98";
        for (Friends cn : contacts) {
            if(!(cn.getName().equals("")))
            {


                myno=cn.getNameDD();
            }

        }






        {


            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                    .setFastestInterval(1000);
            // Request location updates

            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
            Log.d("reque", "--->>>>");




















if (flood.equals("1"))


                        alertmyfriend();





        }




        // Create the location request if person's last location is of disaster

    }
    // sends sms to emergency contacts automaticaaly with all the details of where the phone was last spotted
    private void alertmyfriend() {
  //      Intent ij=new Intent(this,MainalertActivity.class);
    //    startActivity(ij);





    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.e("lococococ", String.valueOf(location.getLatitude()));
        latlong=location.getLatitude()+","+location.getLongitude();
        lat=location.getLatitude();
        longi=location.getLongitude();
        go();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            long diffTime = (curTime - lastupdate);

            if ((curTime - lastupdate) > 100) {



                float speed = Math.abs(x + y + z - lastx - lasty - lastz)/ diffTime * 10000;
                Log.v("speeed",speed+"" );
                if (speed > 2000) {
//TODO alert
                    MainActivity.flood = "1";
                    SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                    editor.putString("flood", "1");
                    editor.apply();
                }

                lastx = x;
                lasty = y;
                lastz = z;




                lastupdate = curTime;
            }
        }

        if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            Log.e("temp",sensorEvent.values[0]+"");
            if (sensorEvent.values[0]>50){ MainActivity.flood = "1";
                SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                editor.putString("flood", "1");
                editor.apply();}
        }










    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.e("accuracyyy",""+accuracy);
    }


    private class FriendsProcessor1 extends AsyncTask<String, Void, Integer> {



        public FriendsProcessor1() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise

                    Log.e("responseeee",response.replace(" ",""));
                    JSONObject obj=new JSONObject(response.replace(" ",""));

                    String ele = (obj.getString("snappedPoints"));
                    String[] elee=ele.split(",");
                    String elv=elee[0].replace("[{\"location\":{\"latitude\":","");
                    String elv1=elee[1].replace("\"longitude\":","").replace("}","");
                    Log.e("lat",elv);
                    Log.e("longi",elv1);

                    String latlongnew=elv+","+elv1;
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
//TODO
                    Call call = apiService.getall(latlongnew,API_KEY);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.e(TAG,"success");
                            Log.e(TAG,  response.raw().request().url().toString());
                            String url=response.raw().request().url().toString();
                            FriendsProcessor mytask = new FriendsProcessor();
                            mytask.execute(url);



                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.e(TAG,"failureee");
                        }


                    });


































                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }













    void goapii()
    {



        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e(TAG, "whyy");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//TODO
        Call call = apiService.getall(latlong, API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                FriendsProcessor mytask = new FriendsProcessor();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });


        if (API_KEY1.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e(TAG, "whyy");
        ApiInterface1 apiService1 =
                ApiClient.getClient1().create(ApiInterface1.class);
//TODO
        Call call1 = apiService1.getall(latlong, API_KEY1);
        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                FriendsProcessor1 mytask = new FriendsProcessor1();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });





    }


    void goapibloodbank()
    {
        ApiInterfaceblood apiService = ApiClientblood.getClient().create(ApiInterfaceblood.class);
//TODO
        Call call = apiService.getall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Log.e("blll", url);
                BloodB mytask = new BloodB();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });

    }

    private class BloodB extends AsyncTask<String, Void, Integer> {



        public BloodB() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);



                    // now process the string using the method that we implemented in the previous exercise
                    String[] res=response.split("],");
                    for(int i=1;i<res.length;i++)
                    {String[] r=res[i].split(",\"");

                        if(r[6].replace("\"","").equals(ZIP)) {

                            bloodno=r[7].replace("\"","");
                            bloodloc=r[r.length-2].replace("\"","")+","+r[r.length-1].replace("\"","");

                            bbname=r[1].replace("\"","");
                            blba=""+r[1].replace("\"","");
                            blba1=""+r[5].replace("\"","");
                            blba11=""+r[7].replace("\"","");
                        }
                    }

/*
                    String ele = (obj.getJSONArray("results")).toString();
                    JSONObject obj1=new JSONObject(ele.replaceFirst("\\[",""));
                    String eee=obj1.getJSONArray("address_components").toString();
                    Log.e("resppp",eee);
                    JSONArray arr=obj1.getJSONArray("address_components");
                    for(int i=0;i<arr.length();i++){
                        if(arr.getString(i).contains("postal_code"))
                            Log.e("respppp", arr.getString(i));
                        JSONObject obj11=new JSONObject(arr.getString(i));
                        Log.e("resppppp", obj11.getString("long_name"));
                        ZIP=obj11.getString("long_name");
                        goapiihospital();


                    }*/


                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }













































    void goapipin()
    {

        if (API_KEYpin.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterfacepin apiService = ApiClientpin.getClient().create(ApiInterfacepin.class);
//TODO
        Call call = apiService.getall(latlong,API_KEYpin);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Log.e(TAG, url);
                Pincode mytask = new Pincode();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }

    private class Pincode extends AsyncTask<String, Void, Integer> {



        public Pincode() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise
                    JSONObject obj=new JSONObject(response.replace(" ",""));

                    String ele = (obj.getJSONArray("results")).toString();
                    JSONObject obj1=new JSONObject(ele.replaceFirst("\\[",""));
                    String eee=obj1.getJSONArray("address_components").toString();
                    JSONArray arr=obj1.getJSONArray("address_components");
                    for(int i=0;i<arr.length();i++){

                        JSONObject obj11=new JSONObject(arr.getString(i));
                        ZIP=obj11.getString("long_name");
                        goapiihospital();
                        goapibloodbank();


                    }


                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }


























































//hospital



    void goapiihospital()
    {




        ApiInterfacehosp apiService = ApiClienthospital.getClient().create(ApiInterfacehosp.class);
//TODO
        Call call = apiService.getall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Hospitalprocessor mytask = new Hospitalprocessor();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }

    private class Hospitalprocessor extends AsyncTask<String, Void, Integer> {



        public Hospitalprocessor() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise
                    JSONObject obj=new JSONObject(response);
                    String data=obj.getString("data");
                    String[] dataa=data.split("\\[");
                    for(int i=3;i<1050;i++) {
                        String[] pin = dataa[i].split("\"");
                        String[] pinloc = dataa[i].split(",\"");
                        if(pin[16].replace(",","").equals(ZIP)) {
                            emergencyno=pin[17];
                            hosp=pin[1];
                            hospname=pin[1].replace("\"","");
                            hospp=""+pin[1].replace("\"","");
                            hospp1=""+pin[9].replace("\"","");
                            hospp11=""+emergencyno;

                        }
                    }                        result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }

























































































    private class FriendsProcessor extends AsyncTask<String, Void, Integer> {



        public FriendsProcessor() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise

                    Log.e("responseeee",response.replace(" ",""));
                    JSONObject obj=new JSONObject(response.replace(" ",""));
                    if(obj.getString("status").equals("OK")) {
                        String ele = (obj.getString("results"));
                        String[] elee=ele.split(",");
                        String elv=elee[0].replace("[{\"elevation\":","");
                        Log.e("elevation",elv);
                        if(!(eleva==0)){if(Double.valueOf(elv)>0)
                            diffelevation=eleva-Double.valueOf(elv);
                        else  diffelevation=eleva+Double.valueOf(elv);
                        }
                        else {if(Double.valueOf(elv)>0)
                            eleva =  Double.valueOf(elv)-eleva;
                        else  eleva = eleva + Double.valueOf(elv);
                        }                      Log.e("elelelele", String.valueOf(eleva));

                        //the condition to check if the person is inside the house during earthquake

                        if(diffelevation>2)
                            Log.e("Alert","you are at risk");

                        result = 1; // Successful
                    }
                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";

        String result = "";

        while((line = bufferedReader.readLine()) != null){

            result += line;

        }



            /* Close Stream */

        if(null!=inputStream){

            inputStream.close();

        }

        return result;

    }






















    void go(){





        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
        String username="",name="",myno="98";
        for (Friends cn : contacts) {
            if(!(cn.getName().equals("")))
            {

                username=cn.getName();
                name=cn.getNameD();
                myno=cn.getNameDD();
                Log.e("numbermy",myno);
            }

        }







        SharedPreferences preferenceflood=getSharedPreferences("flood",MODE_PRIVATE);
        flood=preferenceflood.getString("flood","0");




    }






    public static class ApiClientdead {

        public static final String BASE_URL ="https://api-us.faceplusplus.com/facepp/v3/";

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

    }
    public interface ApiInterfacedead {

        @POST("search")
        retrofit2.Call<ResponseBody> getall(@Query("api_key") String code,
                                            @Query("api_secret") String monthact,
                                            // @Query("image_file") File file,
                                          //  @Query("image_base64") String img_uri,

                                            //   @Query("image_file") File file,
                                             @Query("image_url") String url,
                                            @Query("faceset_token") String faceset
        );


    }
















    public static class ApiClienthospital {

        public static final String BASE_URL ="https://data.gov.in/node/356921/datastore/export/";

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }


    }
    public interface ApiInterfacehosp {

        @GET("json")
        Call<ResponseBody> getall();

    }













    public static class ApiClient {

        public static final String BASE_URL ="https://maps.googleapis.com/maps/api/elevation/";
        public static final String BASE_URL1 ="https://roads.googleapis.com/v1/";
        private static Retrofit retrofit = null;
        private static Retrofit retrofit1 = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }




        public static Retrofit getClient1() {
            if (retrofit1==null) {
                retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE_URL1)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit1;
        }



    }



    public interface ApiInterface {

        @GET("json?")
        Call<ResponseBody> getall(@Query("locations") String loc, @Query("key") String apiKey);

    }

    public interface ApiInterface1 {

        @GET("nearestRoads?"+"")
        Call<ResponseBody> getall(@Query("points") String point,@Query("key") String apiKey1);

    }
    public static class ApiClientpin {

        public static final String BASE_URL ="https://maps.googleapis.com/maps/api/geocode/";

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }


    }
    public interface ApiInterfacepin {

        @GET("json?")
        Call<ResponseBody> getall(@Query("latlng") String point,@Query("key") String apiKey11);

    }

    public static class ApiClientblood{

        public static final String BASE_URL ="https://data.gov.in/node/3287321/datastore/export/";

        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }


    }
    public interface ApiInterfaceblood{

        @GET("json")
        Call<ResponseBody> getall();

    }





























































































































// MainActivity







void checkEarthquake()
{

    Earthquake.ApiInterfaceearthquake apiServiceearth = Earthquake.ApiClientearthquake.getClient().create(Earthquake.ApiInterfaceearthquake.class);
//TODO
    Call calle = apiServiceearth.getall();
    calle.enqueue(new Callback() {
        @Override
        public void onResponse(Call calle, Response response) {
            Log.e(TAG, "success");
            Log.e(TAG, response.raw().request().url().toString());
            String url = response.raw().request().url().toString();
            Earthquake.Earthquakefinder mytask = new Earthquake.Earthquakefinder();
            mytask.execute(url);


        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Log.e(TAG, "failureee");
        }


    });

}














































    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();
        if(id==R.id.setting) {

            Intent i=new Intent(MainActivity.this,MainsettingActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.mycomp) {
            Intent i = new Intent(MainActivity.this, Mycomp.class);
            startActivity(i);
        }

        else if (id == R.id.mycacc) {
            mybaccount();
            accountdetail();



        }

        else if (id == R.id.safetyplus) {
           MyService.myaccelerometer=4000;
            SharedPreferences.Editor editor=getSharedPreferences("sensitivity",MODE_PRIVATE).edit();
            editor.putString("no", String.valueOf("2"));

            editor.apply();
            Snackbar.make(rl,"Safety Plus Plus is now activated. Just shake your phone for help!",Snackbar.LENGTH_LONG).show();
sendNotification();


        } else if (id == R.id.Emergencycontacts) {
           emergencycontact();
        }
        else if (id == R.id.settingg) {

            Intent i = new Intent(MainActivity.this, MainsettingActivity.class);
            startActivity(i);
        }


        else if (id==R.id.blueh)
        {
            Intent i = new Intent(MainActivity.this, BeaconTransmitterActivity.class);
            startActivity(i);
        }
        else if (id==R.id.testmy)
        { MainActivity.flood="1";
            SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
            editor.putString("flood","1");

            editor.apply();
            Toast.makeText(getApplicationContext(),"It is a fake test of how app works during any mishap",Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, BeaconTransmitterActivity1.class);
            startActivity(i);

        }
        else if (id==R.id.logout)
        {
            EmailPasswordActivity.sout=1;
            Intent i=new Intent(MainActivity.this,EmailPasswordActivity.class);
            startActivity(i);

        }
        else if (id==R.id.about)
        {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.layoutabout, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setTitle("About ");
            alert.setIcon(R.drawable.ic_question_answer_black_24dp);


            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();











        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private File createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.jskgmail.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 111);


            }

        }}



    private void contactPicked(Intent data) {

        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
// getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
//Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
// column index of the phone number
            int phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
// column index of the contact name
            int nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            Log.d("name",name);
            Log.d("phno",phoneNo);
            naam=naam+name+",";
            phon=phon+phoneNo+",";
            SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
            editor.putString("mob",phon);
            editor.putString("nam",naam);
            editor.apply();
            stringArrayList.add(name);
            stringArrayList1.add(phoneNo);




            adapter=new ListViewAdfrlist(MainActivity.this,stringArrayList,stringArrayList1);
            l.setAdapter(adapter);
// Set the value to the textviews
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case RESULT_PICK_CONTACT:
                contactPicked(data);

            case 111:
                if (resultCode == RESULT_OK) {
                    jsfind();

                }


        }
    }


    private void jsfind()
    {
        Snackbar.make(rl,"Uploading and finding the image in our servers.....",Snackbar.LENGTH_LONG).show();


        new CountDownTimer(3900, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {


                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.js, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setTitle("Image Recognised ");
                alert.setIcon(R.drawable.ic_done_green_24dp);


                alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();




            }
        }.start();








    }















        private void uploadfirebase(Uri file) {
            goapidead("fireurl");
            mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference riversRef = mStorageRef.child("/"+"myface1"+".jpg");

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.e("upl", String.valueOf(downloadUrl));
                         String   fireurl=String.valueOf(downloadUrl);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }


        void goapidead(String fireurl)
    {




        ApiInterfacedead apiService1 = ApiClientdead.getClient().create(ApiInterfacedead.class);
        //TODO

        Call call  = apiService1.getall("EsnO87cQJDQYOtPITEOYRe3RH1QIc6hP","7isv3sM26h9F7WKfCWIevxZyYnjgR_hr","https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/js.jpg?alt=media&token=9eda79c2-56ae-4d89-8c2e-61cb3ddc77da","8718d1210e1c872ab9d1f99c5900b58c");


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Log.e(TAG, url);
                Dead mytask = new Dead();
                mytask.execute(url);



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }
        private class Dead extends AsyncTask<String, Void, Integer> {



            public Dead() {

                super();

            }


            // The onPreExecute is executed on the main UI thread before background processing is

            // started. In this method, we start the progressdialog.

            @Override

            protected void onPreExecute() {

                super.onPreExecute();


                // Show the progress dialog on the screen


            }


            // This method is executed in the background and will return a result to onPostExecute

            // method. It receives the file name as input parameter.

            @Override

            protected Integer doInBackground(String... urls) {


                InputStream inputStream = null;

                HttpURLConnection urlConnection = null;

                Integer result = 0;


                // TODO connect to server, download and process the JSON string


                // Now we read the file, line by line and construct the

                // Json string from the information read in.

                try {

                /* forming th java.net.URL object */

                    URL url = new URL(urls[0]);

                    urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                    urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                    urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                    urlConnection.setRequestMethod("POST");

                    int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                    if (statusCode == 200) {

                        inputStream = new BufferedInputStream(urlConnection.getInputStream());


                        // Convert the read in information to a Json string

                        String response = convertInputStreamToString(inputStream);


                        // now process the string using the method that we implemented in the previous exercise

                        Log.e("ddddoodd",response);


                    }
else  Log.e("ddddoodd","no");
                } catch (Exception e) {

                    Log.d(TAG, e.getLocalizedMessage());

                }

                return result; //"Failed to fetch data!";

            }

        }




























/*
        public interface FileUploadService {
            @Multipart
            @POST("search")
            Call<ResponseBody> upload(
                    @Part("api_key") RequestBody description,
                    @Part("api_secret") RequestBody description1,
                    @Part ("image_url") RequestBody description3,
                    @Part("faceset_token") RequestBody description2
            );
        }

        private void uploadFile(String fireurl) {

            FileUploadService service =
                    ServiceGenerator.createService(FileUploadService.class);



            // add another part within the multipart request

            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "8eXIfwPbVhLUXV4xt9eW2xRSxWt74Fki");
            RequestBody description1 =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "9xyBX7iWUUWu4msZbaAm6_XTRN9OiT5b");
            // MultipartBody.Part is used to send also the actual file name
            RequestBody body =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/images%2Fname.jpg?alt=media&token=f3909169-4461-401f-81ab-4a0f9d30ae6f");

            RequestBody description2 =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "537c2b49a9a160655b9a3c707555af4b");

            // finally, execute the request
            Call<ResponseBody> call = service.upload(description,description1, body,description2);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {





                    Log.e("Uploaddd", "success");
                    Log.e("Uploadd", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        }











*/



    private void recogniseface() {





        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layoutdead, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        TextView t=(TextView)alertLayout.findViewById(R.id.textView82);
        t.setText(responseddd);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setTitle("Identify person ");
        alert.setIcon(R.drawable.ic_add_alert_black_24dp);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();




    }
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }



    @Override
    public void onSliderClick(BaseSliderView slider) {
        if(slider.getBundle().get("extra").equals("Safety Tips: Earthquake"))
        { Intent i=new Intent(MainActivity.this,Main3Activity.class);
            startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Flood"))
        { Intent i=new Intent(MainActivity.this,Main4Activity.class);
            startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Fire"))
        { Intent i=new Intent(this,Main5Activity.class);
            startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Accidents"))
        {  Intent i=new Intent(this,Main6Activity.class);
            startActivity(i);}
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {


        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}




















void emergencycontact()
{
    stringArrayList=new ArrayList<>();
    stringArrayList1=new ArrayList<>();
    LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.layoutemergency, null);

    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    alert.setTitle("Emergency Contacts ");
    alert.setIcon(R.drawable.ic_contacts_black_24dp);
    l=alertLayout.findViewById(R.id.listname);
    FloatingTextButton fab11=alertLayout.findViewById(R.id.floatingActionButton);
    final TextView textView=alertLayout.findViewById(R.id.text);
    fab11.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            textView.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, RESULT_PICK_CONTACT);




        }
    });


    SharedPreferences preferenceflood=getSharedPreferences("flood",MODE_PRIVATE);
    flood=preferenceflood.getString("flood","0");

    SharedPreferences preference=getSharedPreferences("emergency",MODE_PRIVATE);
    phon=preference.getString("mob","");
    naam=preference.getString("nam","");
    if(naam!="") {

        textView.setVisibility(View.INVISIBLE);
        String[] name=naam.split(",");
        String[] no=phon.split(",");
        for(int i=0;i<name.length;i++) {
            stringArrayList.add(name[i]);
            stringArrayList1.add(no[i]);
        }




        ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
        l.setAdapter(adapterj);

    }
    else {

    }
    l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.layoutdelete, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String n=stringArrayList.get(position);
                    String p=stringArrayList1.get(position);
                    stringArrayList.remove(position);
                    stringArrayList1.remove(position);

                    ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
                    l.setAdapter(adapterj);
                    SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
                    editor.putString("mob",phon.replace(p+",",""));
                    editor.putString("nam",naam.replace(n+",",""));
                    editor.apply();




                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();















            return false;
        }
    });

    alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {


        }
    });
    AlertDialog dialog = alert.create();
    dialog.show();







    }





void mybaccount()
{
    LayoutInflater inflater = getLayoutInflater();
    final View alertLayout = inflater.inflate(R.layout.mybacc, null);
    final EditText pubkey=(EditText)alertLayout.findViewById(R.id.editText6);
    final EditText prikey=(EditText)alertLayout.findViewById(R.id.editText7);
    final TextView bal=alertLayout.findViewById(R.id.textView46);
    final Button done=alertLayout.findViewById(R.id.button6);

    final TextView create=alertLayout.findViewById(R.id.textView48);
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);

    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);

    SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
    String pub = prefs.getString("public", null);
if (pub!=null)
{


    pubkey.setText(pub);
}


    SharedPreferences prefs1 = getSharedPreferences("acckeys",MODE_PRIVATE);
    String ball = prefs1.getString("balance", "N.A.");


    bal.setText("BALANCE: "+ball);




    String pri = prefs.getString("private", null);
if (pri!=null)
    prikey.setText(pri);


create.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(MainActivity.this,WebetherActivity.class);
        startActivity(i);
    }
});
    final AlertDialog dialog = alert.create();

done.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
        editor.putString("public",pubkey.getText().toString());
        editor.putString("private",prikey.getText().toString());

        editor.apply();
        dialog.dismiss();

    }
});

    dialog.show();

}





        void accountdetail()
        {
            SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
            String publ = prefs.getString("public", null);
            ApiInterfacecomp apiService =ApiClientcomp.getClient().create(ApiInterfacecomp.class);
//TODO
            Call call = apiService.getall(publ);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                    String url = response.raw().request().url().toString();
                    Log.e("compether", url);
                    Complaint mytask = new Complaint();
                    mytask.execute(url);


                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }


            });

        }




        public static class ApiClientcomp {

            public static final String BASE_URL ="http://54.169.130.29:3000/";

            private static Retrofit retrofit = null;

            public static Retrofit getClient() {
                if (retrofit==null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
                return retrofit;
            }

        }
        public interface ApiInterfacecomp {
            @GET("getbalance/{p1}")
            retrofit2.Call<ResponseBody> getall(

                    @Path("p1") String pubkey
            );


        }







        private class Complaint extends AsyncTask<String, Void, Integer> {



            public Complaint() {

                super();

            }


            // The onPreExecute is executed on the main UI thread before background processing is

            // started. In this method, we start the progressdialog.

            @Override

            protected void onPreExecute() {

                super.onPreExecute();


                // Show the progress dialog on the screen


            }


            // This method is executed in the background and will return a result to onPostExecute

            // method. It receives the file name as input parameter.

            @Override

            protected Integer doInBackground(String... urls) {


                InputStream inputStream = null;

                HttpURLConnection urlConnection = null;

                Integer result = 0;


                // TODO connect to server, download and process the JSON string


                // Now we read the file, line by line and construct the

                // Json string from the information read in.

                try {

                /* forming th java.net.URL object */

                    URL url = new URL(urls[0]);

                    urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                    urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                    urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                    urlConnection.setRequestMethod("GET");

                    int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                    if (statusCode == 200) {

                        inputStream = new BufferedInputStream(urlConnection.getInputStream());


                        // Convert the read in information to a Json string

                        String response = convertInputStreamToString(inputStream);


                        Log.e("compbalanceee",response.replace("\"",""));
                        String[] a=response.replace("\"","").replace(" ","").split("");
                        Log.e("compbalanceee", String.valueOf(a.length));

                        String bal="N.A.";
                        if (a.length==21)
                            bal=a[1]+""+a[2]+"."+a[3]+""+a[4]+""+a[5]+" eth";
                        else if (a.length==20)
                            bal=a[1]+"."+a[2]+""+a[3]+""+a[4]+""+a[5]+" eth";

                        else  if (a.length==22)
                            bal=a[1]+""+a[2]+""+a[3]+"."+a[4]+""+a[5]+" eth";

                        SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
                        editor.putString("balance",bal);

                        editor.apply();



                        result = 1; // Successful

                    } else {

                        result = 0; //"Failed to fetch data!";

                    }

                } catch (Exception e) {



                }

                return result; //"Failed to fetch data!";

            }

        }



@Override
        protected void onResume()
{
    super.onResume();
    accountdetail();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
    String bal = prefs.getString("balance", "N.A.");

    View header=navigationView.getHeaderView(0) ;
    TextView textView=header.findViewById(R.id.text);
    textView.setText("BALANCE: "+bal);

    navigationView.setNavigationItemSelectedListener(this);
}



void my_app_starter()
{


    final TapTargetSequence sequence = new TapTargetSequence(this)
            .targets(
                    TapTarget.forView(findViewById(R.id.bmb), "Emergency button") .transparentTarget(true)     ,
                    TapTarget.forView(findViewById(R.id.rl), "You", "Up") .transparentTarget(true)     ,

                    TapTarget.forView(findViewById(R.id.rl1), "You", "Up") .transparentTarget(true)     )


            .listener(new TapTargetSequence.Listener() {
                // This listener will tell us when interesting(tm) events happen in regards
                // to the sequence
                @Override
                public void onSequenceFinish() {
                    Snackbar.make(rl,"Congratulations! You're all set to use the app!",Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {
                    final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Uh oh")
                            .setMessage("You canceled the sequence")
                            .setPositiveButton("Oops", null).show();
                    TapTargetView.showFor(dialog,
                            TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                    .cancelable(false)
                                    .tintTarget(false), new TapTargetView.Listener() {
                                @Override
                                public void onTargetClick(TapTargetView view) {
                                    super.onTargetClick(view);
                                    dialog.dismiss();
                                }
                            });
                }
            });

sequence.start();
}




        public void sendNotification() {


            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                    notificationIntent, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Safety Plus Plus is active")
                    .setContentText("Just shake your phone when you feel danger")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Just shake your phone when you feel danger. An emergency notification would be sent to nearest police station"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(intent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, mBuilder.build());


        }



}


