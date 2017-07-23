package com.kingsdev.linkapp.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingsdev.linkapp.R;

/**
 * Created by Jeron Kio on 7/23/2017.
 */

public class ActivityLogItem extends LinearLayout {

    ImageView icon;
    TextView tvName, tvDescription;
    String name, description;
    boolean isDonation;

    public ActivityLogItem(Context context) {
        super(context);
        inflate(context, R.layout.item_activity_log, this);

        icon=(ImageView)findViewById(R.id.ivIcon);
        tvName=(TextView)findViewById(R.id.tvName);
        tvDescription=(TextView)findViewById(R.id.tvDescription);

        name="";
        description="";
        isDonation=true;
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

    public boolean isDonation() {
        return isDonation;
    }

    public void setDonation(boolean donation) {
        isDonation = donation;
        if (isDonation)
            icon.setImageResource(R.drawable.ic_donate);
        else
            icon.setImageResource(R.drawable.ic_volunteer);
    }
}
