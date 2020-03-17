package com.example.customerapp.ForgotPassword.PhaseSecond;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp.ForgotPassword.PhaseOne.EmailActivity;
import com.example.customerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTP extends AppCompatActivity {

    EditText receivedOTP, newPassword, confirmPassword;
    Button resetPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        receivedOTP = findViewById(R.id.etotp);
        newPassword = findViewById(R.id.etnewpassword);
        confirmPassword = findViewById(R.id.etconfirmpassword);

        resetPassword = findViewById(R.id.reset);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });
    }

    private void sendOTP(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying the OTP..");
        progressDialog.show();

        final String otp = receivedOTP.getText().toString().trim();
        final String password = confirmPassword.getText().toString().trim();

        Retrofit retrofit = OTPClien.forgetSecond();
        OTPInterface otpInterface = retrofit.create(OTPInterface.class);
        Call<ResponseOTP> responseOTPCall = otpInterface.sendOTP(otp, password);
        responseOTPCall.enqueue(new Callback<ResponseOTP>() {
            @Override
            public void onResponse(Call<ResponseOTP> call, Response<ResponseOTP> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    ResponseOTP resObj = response.body();
                    if (resObj.getStatus().equals("false")){
                        Toast.makeText(OTP.this, response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(OTP.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(OTP.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseOTP> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OTP.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
