package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectVolunteer extends SweetJson {
    public int disaster_id;
    public String username;

    public ConnectVolunteer(int disaster_id, String username) {
        super(); //always call super

        setRoute("android/volunteer"); //set the route
        setSubclassInstance(this);  //never changers

        this.disaster_id = disaster_id;
        this.username = username;
    }
}
