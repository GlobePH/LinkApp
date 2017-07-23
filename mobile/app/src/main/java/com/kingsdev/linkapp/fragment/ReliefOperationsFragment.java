package com.kingsdev.linkapp.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jianastrero.sweetmotherofjson.SweetJson;
import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.MainActivity;
import com.kingsdev.linkapp.R;
import com.kingsdev.linkapp.login.ConnectDonate;
import com.kingsdev.linkapp.login.ConnectGetReliefOperations;
import com.kingsdev.linkapp.login.ConnectVolunteer;
import com.kingsdev.linkapp.ui.ReliefOperationItem;
import com.kingsdev.linkapp.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReliefOperationsFragment extends Fragment {

    LinearLayout parent;
    ArrayList<ReliefOperationItem> reliefOperationItems;

    public ReliefOperationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_relief_operations, container, false);

        SweetJsonConfig.setDomain(Util.URL);

        parent=(LinearLayout)v.findViewById(R.id.parent);
        reliefOperationItems=new ArrayList<>();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getReliefOperations();
    }

    public void update() {
        parent.removeAllViews();
        for (int i=0; i<reliefOperationItems.size(); i++) {
            parent.addView(reliefOperationItems.get(i));
        }
    }


    public void getReliefOperations() {
        ConnectGetReliefOperations getReliefOperations=new ConnectGetReliefOperations();
        getReliefOperations.setOnConnectionListener(new SweetJson.OnConnectionListener() {
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
                    reliefOperationItems.clear();
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jo=jsonArray.getJSONObject(i);

                        final int id=jo.getInt("id");
                        final String name=jo.getString("name");
                        final String description=jo.getString("description");
                        final String address=jo.getString("address");

                        ReliefOperationItem reliefOperationItem=new ReliefOperationItem(getContext());
                        reliefOperationItem.setId(id);
                        reliefOperationItem.setName(name);
                        reliefOperationItem.setDescription(description);
                        reliefOperationItem.setAddress(address);

                        reliefOperationItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Util.confirm(getContext(), name,
                                        description + "\n\nDo you want to donate or volunteer for " + name + "?",
                                        "Volunteer", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                volunteer(id);
                                            }
                                        },
                                        "Donate PHP 20.00", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                donate(id, 20);
                                            }
                                        });
                            }
                        });

                        reliefOperationItems.add(reliefOperationItem);

                        update();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    getReliefOperations();
                }
                progressDialog.dismiss();
            }
        });
        getReliefOperations.submit();
    }

    public void volunteer(final int disasterId) {
        ConnectVolunteer volunteer=new ConnectVolunteer(disasterId, Util.getUser(getContext())[0]);
        volunteer.setOnConnectionListener(new SweetJson.OnConnectionListener() {
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
                    int a=Integer.parseInt(result);
                    if (a==1) {
                        Util.snackToast(parent, "Successfully Volunteered!", Util.SNACK_TOAST_SUCCESS);
                    } else {
                        Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    volunteer(disasterId);
                }
                progressDialog.dismiss();
                getReliefOperations();
            }
        });
        volunteer.submit();
    }

    public void donate(final int disasterId, int amount) {
        ConnectDonate donate=new ConnectDonate(disasterId, Util.getUser(getContext())[0], amount);
        donate.setOnConnectionListener(new SweetJson.OnConnectionListener() {
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
                    int a=Integer.parseInt(result);
                    if (a==1) {
                        Util.snackToast(parent, "Successfully Donated PHP 20.00!", Util.SNACK_TOAST_SUCCESS);
                    } else {
                        Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    volunteer(disasterId);
                }
                progressDialog.dismiss();
                getReliefOperations();
            }
        });
        donate.submit();
    }
}
