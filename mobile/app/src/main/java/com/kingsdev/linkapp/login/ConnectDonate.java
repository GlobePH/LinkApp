package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectDonate extends SweetJson {
    public int disaster_id;
    public String username;
    public int amount;

    public ConnectDonate(int disaster_id, String username, int amount) {
        super(); //always call super

        setRoute("android/deposit"); //set the route
        setSubclassInstance(this);  //never changers

        this.disaster_id = disaster_id;
        this.username = username;
        this.amount = amount;
    }
}
