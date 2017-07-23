package com.kingsdev.linkapp.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.R;
import com.kingsdev.linkapp.util.Util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    WebView webview;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SweetJsonConfig.setDomain(Util.URL);

        View v=inflater.inflate(R.layout.fragment_news_feed, container, false);

        webview=(WebView)v.findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("http://"+Util.URL+"/timeline");

        return v;
    }

}
