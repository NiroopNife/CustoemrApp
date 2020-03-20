package com.example.customerapp.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import com.example.customerapp.LocationFinder.MapsActivity;
import com.example.customerapp.Profile.ProfileActivity;
import com.example.customerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    Button profileNav;
    CardView lookForMyKid;
    String schoolname, guardianname, guardianphone, guardianaddress, kid, parentid, driverId,
            studentname, studentadmissionnumber, studentclassnumber, studentsectionname, studentrollnumber;
    TextView schoolName, guardinaName, guardianPhone, guardianAddress, locLatitude, locLongitude,
            studentName, studentAdmissionNumber, studentClassNumber, studentSectionName, studentRollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        schoolName = findViewById(R.id.schoolname);

        guardinaName = findViewById(R.id.guardianname);
        guardianPhone = findViewById(R.id.guardianphone);
        guardianAddress = findViewById(R.id.guardianaddress);

        studentName = findViewById(R.id.studentname);
        studentAdmissionNumber = findViewById(R.id.admissionnumber);
        studentClassNumber = findViewById(R.id.classnumber);
        studentSectionName = findViewById(R.id.sectionname);
        studentRollNumber = findViewById(R.id.rollnumber);

        locLatitude = findViewById(R.id.latitude);
        locLongitude = findViewById(R.id.longitude);

        SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
        driverId = sharedPreferences.getString("driver_id", "");
        guardianname = sharedPreferences.getString("ap_guardian_name", "");
        guardianphone = sharedPreferences.getString("ap_guardian_phone", "");
        guardianaddress = sharedPreferences.getString("ap_guardian_address", "");
        parentid = sharedPreferences.getString("ap_pkid", "");
        kid = sharedPreferences.getString("student_fkid", "");
        schoolname = sharedPreferences.getString("ap_school_fkid", "");
        studentadmissionnumber = sharedPreferences.getString("as_adminssion_no", "");
        studentname = sharedPreferences.getString("as_fname", "");
        studentclassnumber = sharedPreferences.getString("class_name", "");
        studentsectionname = sharedPreferences.getString("sec_name", "");
        studentrollnumber = sharedPreferences.getString("as_roll_no", "");

        profileNav = findViewById(R.id.editprofile);
        profileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfile();
            }
        });


        lookForMyKid = findViewById(R.id.locate);
        lookForMyKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookForKid();
            }  
        });

        guardinaName.setText(guardianname);
        guardianPhone.setText(guardianphone);
        guardianAddress.setText(guardianaddress);
        studentName.setText(studentname);
        studentAdmissionNumber.setText(studentadmissionnumber);
        studentClassNumber.setText(studentclassnumber);
        studentSectionName.setText(studentsectionname);
        studentRollNumber.setText(studentrollnumber);

    }

    private void toProfile(){
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        intent.putExtra("ap_guardian_name", guardianname);
        intent.putExtra("ap_guardian_phone", guardianphone);
        intent.putExtra("ap_guardian_address", guardianaddress);
        intent.putExtra("ap_pkid", parentid);
        intent.putExtra("student_fkid", kid);
        intent.putExtra("ap_school_fkid", schoolname);
        startActivity(intent);

    }

    private void lookForKid(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Constant.Route_URL + "/" + schoolname + "/" + driverId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String pdlatitude, pdlongitude, location;
                        ArrayList<String> pd_lat= new ArrayList<String>();
                        ArrayList<String> pd_lng= new ArrayList<String>();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray result = object.getJSONArray("res");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject jsonObject = result.getJSONObject(i);
                                pdlatitude = jsonObject.getString("pdloc_latitude");
                                pdlongitude = jsonObject.getString("pdloc_longitude");
                                location = jsonObject.getString("pdloc_name");
                                pd_lat.add(jsonObject.getString("pdloc_latitude"));
                                pd_lng.add(jsonObject.getString("pdloc_longitude"));
                                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                                intent.putExtra("pdloc_latitude", pdlatitude);
                                intent.putExtra("student_fkid", kid);
                                intent.putExtra("pdloc_longitude", pdlongitude);
                                intent.putExtra("pd_lat", pd_lat);
                                intent.putExtra("pd_lng", pd_lng);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);

    }
}
