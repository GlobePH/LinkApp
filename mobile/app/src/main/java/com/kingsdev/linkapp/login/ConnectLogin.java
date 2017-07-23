package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectLogin extends SweetJson {
    public String username;
    public String password;

    public ConnectLogin(String username, String password) {
        super(); //always call super

        setRoute("android/login"); //set the route
        setSubclassInstance(this);  //never changers

        this.username = username;
        this.password = password;
    }
}
