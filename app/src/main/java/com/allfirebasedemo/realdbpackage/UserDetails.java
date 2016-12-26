package com.allfirebasedemo.realdbpackage;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sanwal Singh on 20/12/16.
 */

@IgnoreExtraProperties
public class UserDetails {

    public String name, email, phone, address;

    public UserDetails() {
    }

    public UserDetails(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
