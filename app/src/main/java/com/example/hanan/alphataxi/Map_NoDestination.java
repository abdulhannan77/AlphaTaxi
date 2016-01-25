package com.example.hanan.alphataxi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 12/16/2015.
 */
public class Map_NoDestination extends Activity implements LocationListener, View.OnClickListener {
    LocationManager locationManager;
    private static Location startupLocation, bestLocation = null;
    private Spinner spinnerGratuityValue;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    FrameLayout progressBarHolder;


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    String gratuity;
    GoogleMap googleMap;
    private double destLatitude = 31.4697912;
    private double destLongitude = 74.2714405;
    private Button  back, logout;
    ArrayList<String> gratuityValues = new ArrayList<String>();
    double fare, dist, fareWithGratuity = 0.0;
    MarkerOptions marker;
    ArrayAdapter adapter;
    List<String> providers;
    private static String completeRoute;
    private static double distance = 00.00;
    String time;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private static boolean isFirstCall = true;
    boolean notstarted = true;

    DecimalFormat df = new DecimalFormat("#.00");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.only_map);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//
//            destLatitude = bundle.getDouble("destLatitude");
//            destLongitude = bundle.getDouble("destLongitude");
//        }

        registerViews();

        /*{
            public View getView(int position, View convertView,ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

          //      ((TextView) v).setTextSize(16);

                return v;

            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {

                View v = super.getDropDownView(position, convertView,parent);

                ((TextView) v).setGravity(Gravity.CENTER);

                return v;

            }
        };*/

        //    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        //    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //   googleMap.getUiSettings().setZoomGesturesEnabled(true);
        //   googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.setMyLocationEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private Location getCustomLastKnownLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != getApplication().getPackageManager().PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != getApplication().getPackageManager().PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            return bestLocation;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, Map_NoDestination.this);
            providers = locationManager.getProviders(true);

            for (String provider : providers) {

                //    Location l = locationManager.get(provider);
                //    Location l = locationManager.getLastKnownLocation(provider);

                Location l = locationManager.getLastKnownLocation(provider);
                //  Log.d("last known location, provider: %s, location: %s", provider,l);

                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    //          Log.d("found best last known location: %s", l);
                    bestLocation = l;
                }
                if (isFirstCall) {
                    startupLocation = bestLocation;
                    destLatitude=bestLocation.getLatitude();
                    destLongitude=bestLocation.getLongitude();
                }
            }
            if (bestLocation == null) {
                return null;
            }
        }
        return bestLocation;

    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&waypoints=optimize:true|");
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("|");
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));

        urlString.append("&key=AIzaSyCTyeDKtmCL1ZHDpILgzd3VEe7YizwrySg");
        urlString.append("&sensor=false&units=imperial&mode=driving&alternatives=true");
        return urlString.toString();
    }

    public void drawPath(String result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = googleMap.addPolyline(new PolylineOptions()
                            .addAll(list)
                            .width(6)
                            .color(Color.BLUE)//Google maps Red color
                            .geodesic(true)
            );

           /*
           for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }
           */
        } catch (JSONException e) {

        }
    }

    public void drawFullPath(String result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

//            JSONArray distanceArrray = json.getJSONArray("distance");
//            JSONObject Jdistance = distanceArrray.getJSONObject(0);
//            distance=Jdistance.getString("text");

            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = googleMap.addPolyline(new PolylineOptions()
                            .addAll(list)
                            .width(6)
                            .color(Color.RED)//Google maps Red color
                            .geodesic(true)
            );

           /*
           for(int z = 0; z<list.size()-1;z++){
                LatLng src= list.get(z);
                LatLng dest= list.get(z+1);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude,   dest.longitude))
                .width(2)
                .color(Color.BLUE).geodesic(true));
            }
           */
        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onLocationChanged(Location location) {
        bestLocation = location;
//        if(!notstarted) {
        new connectAsyncTask().execute();
        cameraUpdate();
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_btn:
                v.startAnimation(buttonClick);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set title
                alertDialogBuilder.setTitle("ALert");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are You Sure You Want To Logout !")

                        .setCancelable(true)
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("Status", false);
                                editor.apply();
                                startActivity(new Intent(Map_NoDestination.this, MainActivity.class));
                            }
                        })
                ;

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (positive_button != null) {

                    positive_button.setTextColor(this.getResources()
                            .getColor(android.R.color.holo_red_dark));
                }
                Button negative_button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (negative_button != null) {

                    positive_button.setTextColor(this.getResources()
                            .getColor(android.R.color.holo_green_dark));
                }


                break;
            case R.id.back_btn:
                v.startAnimation(buttonClick);
                finish();
                break;
        }


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MapMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hanan.alphataxi/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MapMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hanan.alphataxi/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;

        /*        String url;
                connectAsyncTask(String urlPass){
                    url = urlPass;
                }*/
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);

            progressDialog = new ProgressDialog(Map_NoDestination.this);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            if (isFirstCall) {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json;
            if (isFirstCall) {
                //    kalma chown lat,lon = 31.5036774, 74.3294889
                json = jParser.getJSONFromUrl(makeURL(bestLocation.getLatitude(), bestLocation.getLongitude(), destLatitude, destLongitude));


                json = jParser.getJSONFromUrl(makeURL(bestLocation.getLatitude(), bestLocation.getLongitude(), destLatitude, destLongitude));
                distance = jParser.dist;

            }
            else {

                json = jParser.getJSONFromUrl(makeURL(startupLocation.getLatitude(), startupLocation.getLongitude(), bestLocation.getLatitude(), bestLocation.getLongitude()));
                dist = jParser.dist;


            }

            time = jParser.time;
            return json;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);

            progressDialog.cancel();
            if (result != null) {


                //       float rs[] = null;
                //      Location.distanceBetween(bestLocation.getLatitude(), bestLocation.getLongitude(), 31.4793992, 74.2779138, rs);
                //  if(dist != 0){
              /*  Location destination = new Location("locat");
                destination.setLatitude(31.4793992);
                destination.setLongitude(74.2779138);
                double distInMiles = (bestLocation.distanceTo(destination)) /1000;*/
                //   bestLocation.distanceTo(destination);
                //      textViewMilesValue.setText(""+distance(bestLocation.getLatitude(), bestLocation.getLongitude(),31.5236903, 74.3204486));
                if (isFirstCall) {
                    completeRoute = result;
                }
                //distance amount

                // fare = dist * (1);
//                distance = distance(bestLocation.getLatitude(), bestLocation.getLongitude(), destLatitude, destLongitude);

                //

                //  gratuity = spinnerGratuityValue.getSelectedItem().toString();
              /*  if (Integer.parseInt(gratuity) != 0){
                    fareWithGratuity = (Integer.parseInt(gratuity)/100)* fare;
                    textViewTotalFareValue.setText(""+fareWithGratuity);
                }
                else{
                    textViewTotalFareValue.setText(""+df.format(fare));
                }*/
                drawFullPath(completeRoute);
                if (!isFirstCall) {
                    drawPath(result);
                }

                //    textViewTimeValue.setText(time);

                //    }

               /* new CountDownTimer(1000, 20000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                                           }
                }.start();*/

            }
            isFirstCall = false;
        }
    }

    private void registerViews() {
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/digital7.ttf");

        logout = (Button) findViewById(R.id.logout_btn);
        logout.setOnClickListener(this);
        back = (Button) findViewById(R.id.back_btn);
        back.setOnClickListener(this);

    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins / 60;
            secs = secs % 60;

        }

    };

    private void cameraUpdate() {
        if (isFirstCall) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude()), 5));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude()))      // Sets the center of the map to location user
                    .zoom(16)                   // Sets the zoom
                            //        .bearing(90)                // Sets the orientation of the camera to east
                            //        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        marker = new MarkerOptions().position(new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude())).title("Hello Maps");

// Changing marker icon
        //     marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker_icon)));

// adding marker
        googleMap.clear();
        googleMap.addMarker(marker);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFirstCall = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Criteria criteria = new Criteria();

            if (getCustomLastKnownLocation() != null) {
                cameraUpdate();
                new connectAsyncTask().execute();
            }
        }
    }
}
