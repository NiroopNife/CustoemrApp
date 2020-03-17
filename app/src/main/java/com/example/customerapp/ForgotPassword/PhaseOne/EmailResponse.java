package com.example.customerapp.ForgotPassword.PhaseOne;

import com.google.gson.annotations.SerializedName;

public class EmailResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
