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

    public UserDetails(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
