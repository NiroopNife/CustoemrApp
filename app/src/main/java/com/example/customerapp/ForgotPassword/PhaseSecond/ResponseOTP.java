package com.example.customerapp.ForgotPassword.PhaseSecond;

import com.google.gson.annotations.SerializedName;

public class ResponseOTP {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
