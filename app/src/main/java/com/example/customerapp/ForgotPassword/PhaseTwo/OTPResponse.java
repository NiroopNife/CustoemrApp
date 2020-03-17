package com.example.customerapp.ForgotPassword.PhaseTwo;

import com.google.gson.annotations.SerializedName;

public class OTPResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
