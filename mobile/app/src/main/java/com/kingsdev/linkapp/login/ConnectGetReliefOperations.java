package com.kingsdev.linkapp.login;

import com.jianastrero.sweetmotherofjson.SweetJson;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ConnectGetReliefOperations extends SweetJson {
    public ConnectGetReliefOperations() {
        super(); //always call super

        setRoute("android/relief_operation_list"); //set the route
        setSubclassInstance(this);  //never changers
    }
}
