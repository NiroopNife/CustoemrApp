package com.example.customerapp.Login;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("resp")
    public List<UserDetails> resp =new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class UserDetails{

        @SerializedName("ap_pkid")
        private String ap_pkid;

        @SerializedName("add_dv_driver")
        private String add_dv_driver;

        @SerializedName("pdloc_name")
        private String pdloc_name;

        @SerializedName("pdloc_latitude")
        private String pdloc_latitude;

        @SerializedName("pdloc_longitude")
        private String pdloc_longitude;

        @SerializedName("ap_school_fkid")
        private String ap_school_fkid;

        @SerializedName("as_roll_no")
        private String as_roll_no;

        @SerializedName("as_adminssion_no")
        private String as_adminssion_no;

        @SerializedName("as_fname")
        private String as_fname;

        @SerializedName("as_lname")
        private String as_lname;

        @SerializedName("student_fkid")
        private Integer student_fkid;

        @SerializedName("ap_father_fname")
        private String ap_father_fname;

        @SerializedName("ap_father_phone")
        private String ap_father_phone;

        @SerializedName("ap_father_occupation")
        private String ap_father_occupation;

        @SerializedName("ap_mather_fname")
        private String ap_mather_fname;

        @SerializedName("ap_mother_phone")
        private String ap_mother_phone;

        @SerializedName("ap_mother_occupation")
        private String ap_mother_occupation;

        @SerializedName("ap_guardian_is")
        private String ap_guardian_is;

        @SerializedName("ap_guardian_name")
        private String ap_guardian_name;

        @SerializedName("ap_guardian_phone")
        private String ap_guardian_phone;

        @SerializedName("ap_guardian_address")
        private String ap_guardian_address;

        @SerializedName("parent_username")
        private String parent_username;

        @SerializedName("parent_password")
        private String parent_password;

        @SerializedName("otp")
        private String otp;

        @SerializedName("ap_cdate")
        private String ap_cdate;

        @SerializedName("ap_mdate")
        private String ap_mdate;

        @SerializedName("ap_isactive")
        private Integer ap_isactive;

        @SerializedName("class_name")
        private String class_name;

        @SerializedName("sec_name")
        private String sec_name;

        public String getAp_pkid() {
            return ap_pkid;
        }

        public String getAdd_dv_driver() {
            return add_dv_driver;
        }

        public void setAdd_dv_driver(String add_dv_driver) {
            this.add_dv_driver = add_dv_driver;
        }

        public String getAs_roll_no() {
            return as_roll_no;
        }

        public void setAs_roll_no(String as_roll_no) {
            this.as_roll_no = as_roll_no;
        }

        public String getAs_adminssion_no() {
            return as_adminssion_no;
        }

        public String getPdloc_name() {
            return pdloc_name;
        }

        public void setPdloc_name(String pdloc_name) {
            this.pdloc_name = pdloc_name;
        }

        public String getPdloc_latitude() {
            return pdloc_latitude;
        }

        public void setPdloc_latitude(String pdloc_latitude) {
            this.pdloc_latitude = pdloc_latitude;
        }

        public String getPdloc_longitude() {
            return pdloc_longitude;
        }

        public void setPdloc_longitude(String pdloc_longitude) {
            this.pdloc_longitude = pdloc_longitude;
        }

        public void setAs_adminssion_no(String as_adminssion_no) {
            this.as_adminssion_no = as_adminssion_no;
        }

        public String getAs_fname() {
            return as_fname;
        }

        public void setAs_fname(String as_fname) {
            this.as_fname = as_fname;
        }

        public String getAs_lname() {
            return as_lname;
        }

        public void setAs_lname(String as_lname) {
            this.as_lname = as_lname;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getSec_name() {
            return sec_name;
        }

        public void setSec_name(String sec_name) {
            this.sec_name = sec_name;
        }

        public void setAp_pkid(String ap_pkid) {
            this.ap_pkid = ap_pkid;
        }

        public String getAp_school_fkid() {
            return ap_school_fkid;
        }

        public void setAp_school_fkid(String ap_school_fkid) {
            this.ap_school_fkid = ap_school_fkid;
        }

        public Integer getStudent_fkid() {
            return student_fkid;
        }

        public void setStudent_fkid(Integer student_fkid) {
            this.student_fkid = student_fkid;
        }

        public String getAp_father_fname() {
            return ap_father_fname;
        }

        public void setAp_father_fname(String ap_father_fname) {
            this.ap_father_fname = ap_father_fname;
        }

        public String getAp_father_phone() {
            return ap_father_phone;
        }

        public void setAp_father_phone(String ap_father_phone) {
            this.ap_father_phone = ap_father_phone;
        }

        public String getAp_father_occupation() {
            return ap_father_occupation;
        }

        public void setAp_father_occupation(String ap_father_occupation) {
            this.ap_father_occupation = ap_father_occupation;
        }

        public String getAp_mather_fname() {
            return ap_mather_fname;
        }

        public void setAp_mather_fname(String ap_mather_fname) {
            this.ap_mather_fname = ap_mather_fname;
        }

        public String getAp_mother_phone() {
            return ap_mother_phone;
        }

        public void setAp_mother_phone(String ap_mother_phone) {
            this.ap_mother_phone = ap_mother_phone;
        }

        public String getAp_mother_occupation() {
            return ap_mother_occupation;
        }

        public void setAp_mother_occupation(String ap_mother_occupation) {
            this.ap_mother_occupation = ap_mother_occupation;
        }

        public String getAp_guardian_is() {
            return ap_guardian_is;
        }

        public void setAp_guardian_is(String ap_guardian_is) {
            this.ap_guardian_is = ap_guardian_is;
        }

        public String getAp_guardian_name() {
            return ap_guardian_name;
        }

        public void setAp_guardian_name(String ap_guardian_name) {
            this.ap_guardian_name = ap_guardian_name;
        }

        public String getAp_guardian_phone() {
            return ap_guardian_phone;
        }

        public void setAp_guardian_phone(String ap_guardian_phone) {
            this.ap_guardian_phone = ap_guardian_phone;
        }

        public String getAp_guardian_address() {
            return ap_guardian_address;
        }

        public void setAp_guardian_address(String ap_guardian_address) {
            this.ap_guardian_address = ap_guardian_address;
        }

        public String getParent_username() {
            return parent_username;
        }

        public void setParent_username(String parent_username) {
            this.parent_username = parent_username;
        }

        public String getParent_password() {
            return parent_password;
        }

        public void setParent_password(String parent_password) {
            this.parent_password = parent_password;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getAp_cdate() {
            return ap_cdate;
        }

        public void setAp_cdate(String ap_cdate) {
            this.ap_cdate = ap_cdate;
        }

        public String getAp_mdate() {
            return ap_mdate;
        }

        public void setAp_mdate(String ap_mdate) {
            this.ap_mdate = ap_mdate;
        }

        public Integer getAp_isactive() {
            return ap_isactive;
        }

        public void setAp_isactive(Integer ap_isactive) {
            this.ap_isactive = ap_isactive;
        }
    }
}
