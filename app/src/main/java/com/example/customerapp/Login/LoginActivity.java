package com.example.customerapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp.ForgotPassword.PhaseOne.EmailActivity;
import com.example.customerapp.Home.HomeActivity;
import com.example.customerapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView forgotPassword;
    EditText editUsername, editPassword;
    ImageButton submit;
    String schoolID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        schoolID = bundle.getString("school_id");

        editUsername = findViewById(R.id.etusername);
        editPassword = findViewById(R.id.etpassword);

        forgotPassword = findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, EmailActivity.class));
            }
        });

        submit = findViewById(R.id.next);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        final String username = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String schoolid = schoolID;

        if (TextUtils.isEmpty(username)) {
            editUsername.setError("Enter your username");
            editUsername.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError("Enter your password");
            editPassword.requestFocus();
            progressDialog.dismiss();
            return;
        }

        Retrofit retrofit = LoginClient.userLogin();
        LoginService service = retrofit.create(LoginService.class);
        Call<LoginResponse> call = service.userLogin(username, password, schoolid);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    String userName = "";
                    String driverNumber = "";
                    String pdLat = "";
                    String pdLng = "";
                    String pdName = "";
                    String schoolName = "";
                    String guardianName = "";
                    String guardianPhone = "";
                    String guardianAddress = "";
                    String parentId = "";
                    String kid = "";
                    String isActive = "";
                    String admissionNumber = "";
                    String studentName = "";
                    String studentClass = "";
                    String studentSection = "";
                    String studentRollNumber = "";
                    progressDialog.dismiss();
                    LoginResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
                        List<LoginResponse.UserDetails> driverDetails = resObj.resp;
                        for (LoginResponse.UserDetails details : driverDetails){
                            userName += details.getParent_username();
                            driverNumber += details.getAdd_dv_driver();
                            pdLat += details.getPdloc_latitude();
                            pdLng += details.getPdloc_longitude();
                            pdName += details.getPdloc_name();
                            parentId += details.getAp_pkid();
                            schoolName += details.getAp_school_fkid();
                            guardianName += details.getAp_guardian_name();
                            guardianPhone += details.getAp_guardian_phone();
                            guardianAddress += details.getAp_guardian_address();
                            kid += details.getStudent_fkid();
                            isActive += details.getAp_isactive();
                            admissionNumber += details.getAs_adminssion_no();
                            studentName += details.getAs_fname() + details.getAs_lname();
                            studentClass += details.getClass_name();
                            studentSection += details.getSec_name();
                            studentRollNumber += details.getAs_roll_no();
                            SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("driver_id", driverNumber);
                            editor.putString("pd_lat", pdLat);
                            editor.putString("pd_lng", pdLng);
                            editor.putString("pd_name", pdName);
                            editor.putString("username", userName);
                            editor.putString("ap_school_fkid", schoolName);
                            editor.putString("ap_guardian_name", guardianName);
                            editor.putString("ap_guardian_phone", guardianPhone);
                            editor.putString("ap_guardian_address", guardianAddress);
                            editor.putString("ap_pkid", parentId);
                            editor.putString("student_fkid", kid);
                            editor.putString("ap_isactive", isActive);
                            editor.putString("as_adminssion_no", admissionNumber);
                            editor.putString("as_fname", studentName);
                            editor.putString("class_name", studentClass);
                            editor.putString("sec_name", studentSection);
                            editor.putString("as_roll_no", studentRollNumber);

                            editor.commit();
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("username", userName);
                        intent.putExtra("ap_school_fkid", schoolName);
                        intent.putExtra("ap_guardian_name", guardianName);
                        intent.putExtra("ap_guardian_phone", guardianPhone);
                        intent.putExtra("ap_guardian_address", guardianAddress);
                        intent.putExtra("ap_pkid", parentId);
                        intent.putExtra("student_fkid", kid);
                        intent.putExtra("ap_isactive", isActive);
                        intent.putExtra("as_adminssion_no", admissionNumber);
                        intent.putExtra("as_fname", studentName);
                        intent.putExtra("class_name", studentClass);
                        intent.putExtra("sec_name", studentSection);
                        intent.putExtra("as_roll_no", studentRollNumber);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
