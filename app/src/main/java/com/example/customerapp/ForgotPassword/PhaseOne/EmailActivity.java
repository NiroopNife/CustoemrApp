package com.example.customerapp.ForgotPassword.PhaseOne;

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

import com.example.customerapp.ForgotPassword.PhaseSecond.OTP;
import com.example.customerapp.ForgotPassword.PhaseTwo.OTPActivity;
import com.example.customerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmailActivity extends AppCompatActivity {

    EditText registeredEmail;
    Button nextStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        registeredEmail = findViewById(R.id.etregemail);

        nextStep = findViewById(R.id.reset);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextStep();
            }
        });

    }

    private void goToNextStep(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending OTP to entered Email..");
        progressDialog.show();

        final String regEmail = registeredEmail.getText().toString().trim();

        if (TextUtils.isEmpty(regEmail)) {
            registeredEmail.setError("Enter your registered Email");
            registeredEmail.requestFocus();
            progressDialog.dismiss();
            return;
        }

        Retrofit retrofit = EmailClient.forgotFirst();
        EmailService service = retrofit.create(EmailService.class);
        Call<EmailResponse> call = service.sendRegisteredEmail(regEmail);
        call.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    EmailResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
                        startActivity(new Intent(EmailActivity.this, OTP.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(EmailActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    progressDialog.dismiss();
                    Toast.makeText(EmailActivity.this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EmailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
