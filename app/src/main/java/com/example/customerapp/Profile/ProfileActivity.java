package com.example.customerapp.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp.Home.HomeActivity;
import com.example.customerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    Button fetchLocation, submitDetails;
    EditText guardianName, guardianPhone, guardianAddress;
    ImageView nameEdit, phoneEdit, addressEdit;
    TextView  tGuardianName, tGuardianPhone, tGuardianAddress, fetchedLatitude, fetchedLongitude;
    String lat, lng, username, schoolname, guardianname, guardianphone, guardianaddress, studentid, isactive, parentid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fetchLocation = findViewById(R.id.fetchlocation);
        fetchedLatitude = findViewById(R.id.latitude);
        fetchedLongitude = findViewById(R.id.longitude);

        SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
        guardianname = sharedPreferences.getString("ap_guardian_name", "");
        guardianphone = sharedPreferences.getString("ap_guardian_phone", "");
        guardianaddress = sharedPreferences.getString("ap_guardian_address", "");
        parentid = sharedPreferences.getString("ap_pkid", "");
        studentid = sharedPreferences.getString("student_fkid", "");
        schoolname = sharedPreferences.getString("ap_school_fkid", "");
//
//        Bundle bundle = getIntent().getExtras();
//        guardianname = bundle.getString("ap_guardian_name");
//        guardianphone = bundle.getString("ap_guardian_phone");
//        guardianaddress = bundle.getString("ap_guardian_address");
//        parentid = bundle.getString("ap_pkid");
//        studentid = bundle.getString("student_fkid");
//        schoolname = bundle.getString("ap_school_fkid");


        tGuardianName = findViewById(R.id.tguardianname);
        tGuardianName.setText(guardianname);
        guardianName = findViewById(R.id.guardianname);

        tGuardianPhone = findViewById(R.id.tguardianphone);
        tGuardianPhone.setText(guardianphone);
        guardianPhone = findViewById(R.id.guardianphone);

        tGuardianAddress = findViewById(R.id.tguardianaddress);
        tGuardianAddress.setText(guardianaddress);
        guardianAddress = findViewById(R.id.guardianaddress);


        nameEdit = findViewById(R.id.nameedit);
        nameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tGuardianName.setVisibility(View.GONE);
                guardianName.setVisibility(View.VISIBLE);
                guardianName.setText(guardianname);
                nameEdit.setVisibility(View.GONE);
            }
        });

        phoneEdit = findViewById(R.id.phoneedit);
        phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tGuardianPhone.setVisibility(View.GONE);
                guardianPhone.setVisibility(View.VISIBLE);
                guardianPhone.setText(guardianphone);
                phoneEdit.setVisibility(View.GONE);
            }
        });

        addressEdit = findViewById(R.id.addressedit);
        addressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tGuardianAddress.setVisibility(View.GONE);
                guardianAddress.setVisibility(View.VISIBLE);
                guardianAddress.setText(guardianaddress);
                addressEdit.setVisibility(View.GONE);
            }
        });

        fetchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProfileActivity.this, InsertMapsActivity.class), 999);
            }
        });

        submitDetails = findViewById(R.id.submit);
        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postProfileData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK){
            fetchedLatitude.setText(data.getStringExtra("latitude"));
            fetchedLongitude.setText(data.getStringExtra("longitude"));
        }
    }

    private void postProfileData(){
        System.out.println("Inside post profile data");
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Updating details...");
        dialog.show();

        final String gName = guardianname;
        final String gPhone = guardianphone;
        final String gAddress = guardianaddress;
        final String parentId = parentid;
        final String studentId = studentid;
        final String schoolId = schoolname;
        final String locLatitude = fetchedLatitude.getText().toString().trim();
        final String locLongitude = fetchedLongitude.getText().toString().trim();


//        if (TextUtils.isEmpty(gName)){
//            guardianName.setError("Name should not be empty");
//            guardianName.requestFocus();
//            dialog.dismiss();
//        }
//
//        if (TextUtils.isEmpty(gPhone)){
//            guardianPhone.setError("Phone number should not be empty");
//            guardianPhone.requestFocus();
//            dialog.dismiss();
//        }
//
//        if (TextUtils.isEmpty(gAddress)){
//            guardianAddress.setError("Address should not be empty");
//            guardianAddress.requestFocus();
//            dialog.dismiss();
//        }
//
//        if (TextUtils.isEmpty(locLatitude)){
//            fetchedLatitude.setError("Please select a Pick/Drop location");
//            fetchedLatitude.requestFocus();
//            dialog.dismiss();
//        }
//
//        if (TextUtils.isEmpty(locLongitude)){
//            fetchedLongitude.setError("Please select a Pick/Drop location");
//            fetchedLongitude.requestFocus();
//            dialog.dismiss();
//        }

        Retrofit retrofit = ProfileClient.sendProfileDetails();
        ProfileService service = retrofit.create(ProfileService.class);
        Call<ProfileResponse> call = service.sendProfileDetails(gName, gPhone, gAddress, parentId, studentId, schoolId, locLatitude, locLongitude);
//        System.out.println(gName+", " +gPhone+", " +gAddress+", " +parentId+", " +studentId+", " +schoolId+", " +locLatitude+", " +locLongitude);
//        System.out.println(gName+", " +gPhone+", " +gAddress+", " +parentId+", " +studentId+", " +schoolId+", " +locLatitude+", " +locLongitude);
//        System.out.println(gName+", " +gPhone+", " +gAddress+", " +parentId+", " +studentId+", " +schoolId+", " +locLatitude+", " +locLongitude);
//        System.out.println(gName+", " +gPhone+", " +gAddress+", " +parentId+", " +studentId+", " +schoolId+", " +locLatitude+", " +locLongitude);
//        System.out.println(gName+", " +gPhone+", " +gAddress+", " +parentId+", " +studentId+", " +schoolId+", " +locLatitude+", " +locLongitude);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    ProfileResponse resObj = response.body();
                    System.out.print(resObj.getVal());
                    System.out.print( " tostring " + resObj.toString());
                    System.out.print( " status " + resObj.getStatus());
                    if (resObj.getStatus().equals("true")){
                        Toast.makeText(ProfileActivity.this, "Posted Successfully", Toast.LENGTH_SHORT).show();

//                        System.out.println(response.body().toString());
//                        System.out.println(response.body().toString());
//                        System.out.println(response.body().toString());
//                        System.out.println(response.body().toString());
//                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
//                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    } else {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
