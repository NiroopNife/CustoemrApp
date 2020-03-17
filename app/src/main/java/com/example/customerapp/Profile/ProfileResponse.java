package com.example.customerapp.Profile;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("val")
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
