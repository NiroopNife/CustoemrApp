package com.example.customerapp.ForgotPassword.PhaseTwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp.Login.LoginActivity;
import com.example.customerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTPActivity extends AppCompatActivity {

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
                passwordReset();
            }
        });

    }

    private void passwordReset() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Comparing the OTP...");
        progressDialog.show();

        final String otpReceived = receivedOTP.getText().toString().trim();
        final String passwordConfirm = confirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(otpReceived)) {
            receivedOTP.setError("Enter the Received OTP");
            receivedOTP.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            confirmPassword.setError("Enter the New password");
            confirmPassword.requestFocus();
            progressDialog.dismiss();
            return;
        }

        Retrofit retrofit = OTPClient.forgotSecond();
        OTPService service = retrofit.create(OTPService.class);
        Call<OTPResponse> call = service.sendreceivedOTP(otpReceived, passwordConfirm);
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    OTPResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")) {
                        startActivity(new Intent(OTPActivity.this, LoginActivity.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(OTPActivity.this, "Entered OTP is incorrect" + response, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(OTPActivity.this, "Entered OTP is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
