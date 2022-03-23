package com.REST2.Webservices2.Versioning;

public class Details {
    private String first_name;
    private String last_name;

    public Details() {
        super();
    }

    public Details(String first_name, String last_name, String phone_no) {
        super();
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_no = phone_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    private String phone_no;

}
