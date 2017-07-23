package com.kingsdev.linkapp.ui;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingsdev.linkapp.R;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ReliefOperationItem extends LinearLayout {

    TextView tvName, tvDescription;
    String name, description, address;
    int id;

    public ReliefOperationItem(Context context) {
        super(context);
        inflate(context, R.layout.item_relief_operation, this);

        tvName=(TextView)findViewById(R.id.tvName);
        tvDescription=(TextView)findViewById(R.id.tvDescription);

        name="";
        description="";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        tvName.setText(""+name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        tvDescription.setText(""+description);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
