package com.example.customerapp.School;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customerapp.Login.LoginActivity;
import com.example.customerapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchoolActivity extends AppCompatActivity {

    EditText schoolName;
    ImageButton nextPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolname);

        schoolName = findViewById(R.id.etschoolname);

        nextPage = findViewById(R.id.next);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSchoolID();
            }
        });
    }

    private void sendSchoolID(){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        final String schoolname = schoolName.getText().toString().trim();

        if (TextUtils.isEmpty(schoolname)){
            schoolName.setError("Please Enter your School ID");
            schoolName.requestFocus();
            dialog.dismiss();
            return;
        }

        Retrofit retrofit = SchoolClient.sendSchoolname();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<SchoolResponse> call = service.sendSchoolname(schoolname);
        call.enqueue(new Callback<SchoolResponse>() {
            @Override
            public void onResponse(Call<SchoolResponse> call, Response<SchoolResponse> response) {
                if (response.isSuccessful()){
                    String schoolid = "";
                    String schoolLat = "";
                    String schoolLng = "";
                    dialog.dismiss();
                    SchoolResponse resObj = response.body();
                    if (resObj.getStatus().equals("true")){
                        List<SchoolResponse.SchoolDetails> schoolDetails = resObj.res;
                        for (SchoolResponse.SchoolDetails details : schoolDetails){
                            schoolid += details.getSchool_id();
                            schoolLat += details.getSchool_pick_latitude();
                            schoolLng += details.getSchool_pick_longitude();
                            SharedPreferences sharedPreferences = getSharedPreferences("SchoolPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("schoolLat", schoolLat);
                            editor.putString("schoolLng", schoolLng);
                            editor.putString("schoolname", schoolid);
                            editor.commit();
                        }
                        Intent intent = new Intent(SchoolActivity.this, LoginActivity.class);
                        intent.putExtra("school_id", schoolid);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<SchoolResponse> call, Throwable t) {

            }
        });
    }
}
