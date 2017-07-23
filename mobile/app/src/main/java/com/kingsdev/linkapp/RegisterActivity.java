package com.kingsdev.linkapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianastrero.sweetmotherofjson.SweetJson;
import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.login.ConnectRegister;
import com.kingsdev.linkapp.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout parent, parentHeader, parentContent;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        parent=(LinearLayout)findViewById(R.id.parent);
        parentHeader=(LinearLayout)findViewById(R.id.parentHeader);
        parentContent=(LinearLayout)findViewById(R.id.parentContent);
        tvTitle=(TextView)findViewById(R.id.tvTitle);

        Util.initFont(this);
        SweetJsonConfig.setDomain(Util.URL);

        parent.removeAllViews();
        parent.addView(parentHeader);
        parentHeader.removeAllViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parentHeader.addView(tvTitle);
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                parentHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)Util.convertDpToPixel(96f, RegisterActivity.this)));
                                parent.addView(parentContent);
                            }
                        });
                    }
                }, 1000);
            }
        }, 1000);
    }

    public void login(View v) {
        Util.startActivityAndFinish(this, LoginActivity.class, "#ffee00", v);
    }

    public void register(View v) {
        String username=((EditText)findViewById(R.id.etUsername)).getText().toString();
        String number=((EditText)findViewById(R.id.etNumber)).getText().toString();
        String password=((EditText)findViewById(R.id.etPassword)).getText().toString();

        if (username.isEmpty() || number.isEmpty() || password.isEmpty()) {
            Util.snackToast(parent, "Please enter all input!", Util.SNACK_TOAST_ERROR);
            return;
        }

        ConnectRegister register=new ConnectRegister(username, number, password);
        register.setOnConnectionListener(new SweetJson.OnConnectionListener() {
            ProgressDialog progressDialog;

            @Override
            public void onBeforeConnectionStart() {
                progressDialog=new ProgressDialog(RegisterActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            public void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray) {
                try {
                    int a=Integer.parseInt(result);
                    if (a==0) {
                        Util.snackToast(parent, "Username Taken!", Util.SNACK_TOAST_ERROR);
                    } else if (a==1) {
                        Util.snackToast(parent, "Successfully Registered!", Util.SNACK_TOAST_SUCCESS);
                        Util.startActivityAndFinish(RegisterActivity.this, LoginActivity.class, "#ffee00", parent);
                    } else {
                        Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                }
                progressDialog.dismiss();
            }
        });
        register.submit();
    }
}
