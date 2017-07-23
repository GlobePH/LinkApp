package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectGetActivityLog extends SweetJson {
    public String username;

    public ConnectGetActivityLog(String username) {
        super(); //always call super

        setRoute("android/get_activity_log"); //set the route
        setSubclassInstance(this);  //never changers

        this.username = username;
    }
}
