package com.example.customerapp.School;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SchoolResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("res")
    public List<SchoolDetails> res = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class SchoolDetails{

        @SerializedName("ad_pkid")
        private String school_id;

        @SerializedName("school_pick_latitude")
        private String school_pick_latitude;

        @SerializedName("school_pick_longitude")
        private String school_pick_longitude;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_pick_latitude() {
            return school_pick_latitude;
        }

        public void setSchool_pick_latitude(String school_pick_latitude) {
            this.school_pick_latitude = school_pick_latitude;
        }

        public String getSchool_pick_longitude() {
            return school_pick_longitude;
        }

        public void setSchool_pick_longitude(String school_pick_longitude) {
            this.school_pick_longitude = school_pick_longitude;
        }
    }
}
