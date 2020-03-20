package com.example.customerapp.LocationFinder;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.customerapp.Constants.Constant;
import com.example.customerapp.Others.HomeActivity;
import com.example.customerapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private final Handler handler = new Handler();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    String newLatitude = "";
    String newLongitude = "";
    Marker marker;
    Runnable runnable;
    int count = 0;
    String schoolid, studentid, schoolName;
    String pdlatitude, pdlongitude;
    String LocationUrl = Constant.BASE_URL+"selectlocation/";
    Marker m = null;
    ArrayList<String> pdLat= new ArrayList<String>();
    ArrayList<String> pdLng= new ArrayList<String>();
    List<MarkerOptions> markers = new ArrayList<MarkerOptions>();
    LatLng studentPDLocation;
    LatLng schoolLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle bundle = getIntent().getExtras();
        schoolid = bundle.getString("ap_school_fkid");
        studentid = bundle.getString("student_fkid");
        pdLat = bundle.getStringArrayList("pd_lat");
        pdLng = bundle.getStringArrayList("pd_lng");

        SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
        schoolName = sharedPreferences.getString("schoolname", "");
        String driverId = sharedPreferences.getString("driver_id", "");
        String schoolLat = sharedPreferences.getString("schoolLat", "");
        String schoolLng = sharedPreferences.getString("schoolLng", "");
        String studentpdLat = sharedPreferences.getString("pd_lat", "");
        String studentpdLng = sharedPreferences.getString("pd_lng", "");
        System.out.println("School name is : "+schoolName+", Driver Id is : "+driverId);

        //PickDrop Location
        double studentPDLatitude = Double.parseDouble(studentpdLat);
        double studentPDLongitude = Double.parseDouble(studentpdLng);
        studentPDLocation = new LatLng(studentPDLatitude, studentPDLongitude);

        newLatitude = String.valueOf(studentPDLatitude);

        //School Location
        double schoolLatitude = Double.parseDouble(schoolLat);
        double schoolLongitude = Double.parseDouble(schoolLng);
        schoolLocation = new LatLng(schoolLatitude, schoolLongitude);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Constant.Route_URL + schoolName + "/" + driverId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(Constant.Route_URL + schoolName + "/" + driverId);
                        String location;
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray result = object.getJSONArray("res");
                            for (int i=0;i<result.length(); i++){
                                JSONObject jsonObject = result.getJSONObject(i);
                                pdlatitude = jsonObject.getString("pdloc_latitude");
                                pdlongitude = jsonObject.getString("pdloc_longitude");
                                location = jsonObject.getString("pdloc_name");
                                MarkerOptions MarkerOptions = new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(pdlatitude), Double.parseDouble(pdlongitude)))
                                        .title(location)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                                mMap.addMarker(MarkerOptions);
                                markers.add(MarkerOptions);
                                mMap.addMarker(new MarkerOptions().position(schoolLocation).title("School").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(studentPDLatitude, studentPDLongitude), 13f));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        doTheAutoRefresh();

        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);

        String url = getDirectionsURL(studentPDLocation, schoolLocation);
        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

    }

    private void getRoute(){

    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Write code for your refresh logic
                doTheAutoRefresh();
//                getLocation();
            }
        }, 5000);
    }


    private String updateDirectionsURL(LatLng origin, LatLng dest){
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String dynamicwaypoint = "";
        for (int i=0;i<pdLat.size(); i++){
            System.out.println("PDLat : - "+pdLat);
            System.out.println("New Latitude"+newLatitude);
            if (pdLat.contains(pdlatitude)){
                dynamicwaypoint += pdLat.get(i)+","+pdLng.get(i)+"|";
                break;
            }

            System.out.println(dynamicwaypoint);
        }

        System.out.println(dynamicwaypoint);
        String waypoints = "waypoints=optimize:true|" + dynamicwaypoint;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin  + "&" + waypoints + "&" + str_dest + "&" + sensor + "&" + mode + "&key=AIzaSyDShQDPYnwYVM242B9dqFVD0jL5cGiRG4s";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;

    }

    private String getDirectionsURL(LatLng origin, LatLng dest){
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String dynamicwaypoint = "";
        for (int i=0;i<pdLat.size(); i++){
            if (pdLat.contains(newLatitude)){
                dynamicwaypoint += pdLat.get(i)+","+pdLng.get(i)+"|";
                break;
            }

            System.out.println(dynamicwaypoint);
        }

        System.out.println(dynamicwaypoint);
        String waypoints = "waypoints=optimize:true|" + dynamicwaypoint;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin  + "&" + waypoints + "&" + str_dest + "&" + sensor + "&" + mode + "&key=AIzaSyDShQDPYnwYVM242B9dqFVD0jL5cGiRG4s";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent launchNextActivity = new Intent(MapsActivity.this, HomeActivity.class);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivity);

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        StringRequest request = new StringRequest(Request.Method.GET, LocationUrl+schoolName+"/"+studentid+"/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response is : "+response);
                Toast.makeText(MapsActivity.this, "Response is "+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("resp");
                    for (int i = 0; i < array.length(); i++) {
                        //JSONObject jsonObject = array.getJSONObject(i);
                        if (array.getJSONObject(i).getString("loc_latitude") != null && array.getJSONObject(i).getString("loc_longitude") != null) {
                            newLatitude = array.getJSONObject(i).getString("loc_latitude").trim();
                            newLongitude = array.getJSONObject(i).getString("loc_longitude").trim();
                            addmarkerToMap(newLatitude, newLongitude);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        if (!newLatitude.isEmpty() && !newLongitude.isEmpty()) {
            Double lat = Double.parseDouble(newLatitude);
            Double lng = Double.parseDouble(newLongitude);
            LatLng latLng = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(latLng).title("I'm Here"));
            Marker m1 = (Marker) m.getTag();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            mMap.animateCamera(location);
        }
    }

    private void addmarkerToMap(String newLat, String newLng) {

        if(m!=null){
            int i= 0;
            mMap.clear();
            for(MarkerOptions mo : markers){

                mMap.addMarker(mo);
                System.out.println(" Looping "+i);
                i++;
            }
            String url = updateDirectionsURL(studentPDLocation, schoolLocation);
            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
        System.out.println(" newLat "+newLat+" newLng "+ newLng);
        double lat = Double.parseDouble(newLat);
        double lng = Double.parseDouble(newLng);
        LatLng latLng = new LatLng(lat, lng);
        //System.out.println(latLng);
        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng)).title("I'm Here");
        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.school_bus_marker);
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(img);
        markerOptions.icon(descriptor);
        m = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, 17);
//        mMap.animateCamera(location);
        new Handler().postDelayed(() -> {
            getLocation();
        }, 10000);

    }

    private void getLocation(){
        Log.d("getLocation", "Refreshed");
//        mMap.clear();
        if (mMap!= null){
            System.out.println("mMap is not null");
            if (marker == null){
                System.out.println("marker is null");
                StringRequest request = new StringRequest(Request.Method.GET, LocationUrl+schoolName+"/"+studentid+"/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(LocationUrl+schoolName+"/"+studentid+"/");
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("resp");
                            for (int i = 0; i < array.length(); i++) {
                                //JSONObject jsonObject = array.getJSONObject(i);
                                if (array.getJSONObject(i).getString("loc_latitude") != null && array.getJSONObject(i).getString("loc_longitude") != null) {
//                                    Toast.makeText(MapsActivity.this, "New Location", Toast.LENGTH_SHORT).show();
                                    newLatitude = array.getJSONObject(i).getString("loc_latitude").trim();
                                    newLongitude = array.getJSONObject(i).getString("loc_longitude").trim();
                                    System.out.println("New Location"+newLatitude+", "+newLongitude);
                                    Toast.makeText(MapsActivity.this, "New Location"+newLatitude+", "+newLongitude, Toast.LENGTH_SHORT).show();
                                    addmarkerToMap(newLatitude, newLongitude);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
                if (!newLatitude.isEmpty() && !newLongitude.isEmpty()) {
                    Double lat = Double.parseDouble(newLatitude);
                    Double lng = Double.parseDouble(newLongitude);
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.school_bus_marker);
                    BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(img);
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("I'm Here").icon(descriptor));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                    mMap.animateCamera(location);
                }
            }

        }

    }

    /*------------------------------------------------------------------------------------------------------------------*/

    //Fetch Polyline
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
