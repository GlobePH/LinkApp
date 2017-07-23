package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectRegister extends SweetJson {
    public String username;
    public String number;
    public String password;

    public ConnectRegister(String username, String number, String password) {
        super(); //always call super

        setRoute("android/register"); //set the route
        setSubclassInstance(this);  //never changers

        this.username = username;
        this.number = number;
        this.password = password;
    }
}
