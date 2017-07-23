package com.kingsdev.linkapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jianastrero.sweetmotherofjson.SweetJson;
import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.R;
import com.kingsdev.linkapp.login.ConnectGetActivityLog;
import com.kingsdev.linkapp.ui.ActivityLogItem;
import com.kingsdev.linkapp.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityLogsFragment extends Fragment {

    LinearLayout parent;
    ArrayList<ActivityLogItem> activityLogItems;

    public ActivityLogsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SweetJsonConfig.setDomain(Util.URL);

        View v=inflater.inflate(R.layout.fragment_activity_logs, container, false);

        parent=(LinearLayout)v.findViewById(R.id.parent);
        activityLogItems=new ArrayList<>();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivityLogs();
    }

    public void update() {
        parent.removeAllViews();
        for (int i=0; i<activityLogItems.size(); i++) {
            parent.addView(activityLogItems.get(i));
        }
    }


    public void getActivityLogs() {
        ConnectGetActivityLog getActivityLog=new ConnectGetActivityLog(Util.getUser(getContext())[0]);
        getActivityLog.setOnConnectionListener(new SweetJson.OnConnectionListener() {
            ProgressDialog progressDialog;

            @Override
            public void onBeforeConnectionStart() {
                progressDialog=new ProgressDialog(getContext());
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            public void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray) {
                try {
                    Util.log("result: "+result);
                    activityLogItems.clear();
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jo=jsonArray.getJSONObject(i);

                        String name=jo.getString("name");
                        double amount=jo.getDouble("amount");

                        ActivityLogItem activityLogItem=new ActivityLogItem(getContext());
                        activityLogItem.setName(name);
                        activityLogItem.setDescription(amount==-1?("You volunteered!"):("You Deposited "+amount));
                        activityLogItem.setDonation(amount!=-1);

                        activityLogItems.add(activityLogItem);

                        update();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    getActivityLogs();
                }
                progressDialog.dismiss();
            }
        });
        getActivityLog.submit();
    }
}
