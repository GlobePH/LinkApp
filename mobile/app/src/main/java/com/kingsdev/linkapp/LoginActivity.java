package com.kingsdev.linkapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianastrero.sweetmotherofjson.SweetJson;
import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.login.ConnectLogin;
import com.kingsdev.linkapp.login.ConnectRegister;
import com.kingsdev.linkapp.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LinearLayout parent, parentHeader, parentContent;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                                parentHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)Util.convertDpToPixel(96f, LoginActivity.this)));
                                parent.addView(parentContent);
                            }
                        });
                    }
                }, 1000);
            }
        }, 1000);

        String[] user=Util.getUser(this);
        if (user[0]!=null && user[1]!=null && user[2]!=null) {
            Util.startActivityAndFinish(LoginActivity.this, MainActivity.class, "#ffee00", parent);
        }
    }

    public void login(View v) {
        final String username=((EditText)findViewById(R.id.etUsername)).getText().toString();
        final String password=((EditText)findViewById(R.id.etPassword)).getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Util.snackToast(parent, "Please enter all input!", Util.SNACK_TOAST_ERROR);
            return;
        }

        ConnectLogin login=new ConnectLogin(username, password);
        login.setOnConnectionListener(new SweetJson.OnConnectionListener() {
            ProgressDialog progressDialog;

            @Override
            public void onBeforeConnectionStart() {
                progressDialog=new ProgressDialog(LoginActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            public void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray) {
                try {
                    if (result.equals("0")) {
                        Util.snackToast(parent, "Invalid username/password please recheck!", Util.SNACK_TOAST_ERROR);
                    } else {
                        Util.snackToast(parent, "Successfully Logged in!", Util.SNACK_TOAST_SUCCESS);
                        Util.saveUser(LoginActivity.this, username, result, password);
                        Util.startActivityAndFinish(LoginActivity.this, MainActivity.class, "#ffee00", parent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.snackToast(parent, "Please check your internet connection!", Util.SNACK_TOAST_ERROR);
                }
                progressDialog.dismiss();
            }
        });
        login.submit();
    }

    public void register(View v) {
        Util.startActivityAndFinish(this, RegisterActivity.class, "#ffee00", v);
    }
}