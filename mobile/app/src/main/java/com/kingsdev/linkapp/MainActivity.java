package com.kingsdev.linkapp;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianastrero.sweetmotherofjson.SweetJsonConfig;
import com.kingsdev.linkapp.adapter.ViewPagerAdapter;
import com.kingsdev.linkapp.fragment.ActivityLogsFragment;
import com.kingsdev.linkapp.fragment.NewsFeedFragment;
import com.kingsdev.linkapp.fragment.ReliefOperationsFragment;
import com.kingsdev.linkapp.util.Util;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout parent, parentHeader, parentContent;
    TextView tvTitle;
    ViewPager viewPager;
    BottomBar bottomBar;

    NewsFeedFragment newsFeedFragment;
    ReliefOperationsFragment reliefOperationsFragment;
    ActivityLogsFragment activityLogsFragment;
    ArrayList<Fragment> fragments;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Util.initFont(this);
        SweetJsonConfig.setDomain(Util.URL);

        parent=(LinearLayout)findViewById(R.id.parent);
        parentHeader=(LinearLayout)findViewById(R.id.parentHeader);
        parentContent=(LinearLayout)findViewById(R.id.parentContent);
        tvTitle=(TextView)findViewById(R.id.tvTitle);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        fragments=new ArrayList<>();
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        newsFeedFragment=new NewsFeedFragment();
        reliefOperationsFragment=new ReliefOperationsFragment();
        activityLogsFragment=new ActivityLogsFragment();

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        fragments.add(newsFeedFragment);
        fragments.add(reliefOperationsFragment);
        fragments.add(activityLogsFragment);
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setDefaultTabPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(1);
        bottomBar.setDefaultTabPosition(1);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_news_feed) {
                    viewPager.setCurrentItem(0);
                } else if (tabId == R.id.tab_relief_operations) {
                    viewPager.setCurrentItem(1);
                } else if (tabId == R.id.tab_activity_logs) {
                    viewPager.setCurrentItem(2);
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                            @Override
                            public void onTabSelected(@IdRes int tabId) {
                                if (tabId == R.id.tab_news_feed) {
                                    viewPager.setCurrentItem(0);
                                } else if (tabId == R.id.tab_relief_operations) {
                                    viewPager.setCurrentItem(1);
                                    reliefOperationsFragment.getReliefOperations();
                                } else if (tabId == R.id.tab_activity_logs) {
                                    viewPager.setCurrentItem(2);
                                    activityLogsFragment.getActivityLogs();
                                }
                            }
                        });
                    }
                });
            }
        }, 2000);

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
                                parentHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)Util.convertDpToPixel(64f, MainActivity.this)));
                                parent.addView(parentContent);
                            }
                        });
                    }
                }, 1000);
            }
        }, 300);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
