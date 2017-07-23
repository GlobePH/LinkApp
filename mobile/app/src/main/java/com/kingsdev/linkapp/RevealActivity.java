package com.kingsdev.linkapp;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kingsdev.linkapp.util.Util;

public class RevealActivity extends AppCompatActivity {

    FrameLayout parent;
    ImageView revealCircle;
    int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        parent=(FrameLayout)findViewById(R.id.parent);

        x=getIntent().getIntExtra("x", 0);
        y=getIntent().getIntExtra("y", 0);

        Drawable mDrawable = Util.getDrawable(this, R.drawable.circle);
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(""+getIntent().getStringExtra("color")), PorterDuff.Mode.MULTIPLY));

        revealCircle=(ImageView)findViewById(R.id.revealCircle);
        revealCircle.setImageDrawable(mDrawable);

        parent.removeAllViews();

        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams((int)Util.convertDpToPixel(100, this), (int)Util.convertDpToPixel(100, this));
        params.leftMargin=x-100;
        params.topMargin=y-100;
        parent.addView(revealCircle, params);

        revealCircle.animate()
                .scaleX(0.0f)
                .scaleY(0.0f)
                .setDuration(0)
                .start();


        revealCircle.animate()
                .scaleX(17.0f)
                .scaleY(17.0f)
                .setDuration(800)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent=null;

                        if (Util.nextActivity!=null) {
                            intent=new Intent(RevealActivity.this, Util.nextActivity);
                        } else {
                            intent=getPackageManager().getLaunchIntentForPackage(getIntent().getStringExtra("packageName"));
                        }

                        startActivity(intent);

                        finish();

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        Util.callingActivity.finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
