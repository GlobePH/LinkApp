package com.kingsdev.linkapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kingsdev.linkapp.R;
import com.kingsdev.linkapp.RevealActivity;
import com.marcinorlowski.fonty.Fonty;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by rane on 5/15/2017.
 */

public class Util {

    public static final String UC_LAUNCHER_BROWSER = "com.kingsdev.uclauncher.BrowserActivity";
    public static String REFRESH_APPS_INTENT_ACTION="com.kingsdev.uclauncher.util.REFRESH_APPS";

    public static Activity callingActivity;
    public static Class nextActivity;

    public final static String URL="192.168.171.218:8000";
    public final static String SQLITE="KINGSDEV";

    public final static long UPDATE_SPEED=1000*30;
    public final static long BLOCKED_APP_TIME_SPEED=1000*60;

    public static final int SNACK_TOAST_ERROR=Color.parseColor("#DD4B39");
    public static final int SNACK_TOAST_WARNING=Color.parseColor("#FEAA0C");
    public static final int SNACK_TOAST_SUCCESS=Color.parseColor("#00A65A");
    public static final int SNACK_TOAST_NORMAL=Color.parseColor("#1A2226");

    public static void log(String message) {
        Log.e("com.kingsdev", message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, ""+message, Toast.LENGTH_LONG).show();
    }

    public static void snackToast(View view, String message, int type) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(type);
        TextView textView=(TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextSize(18f);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void initFont(Activity activity) {
        Fonty
            .context(activity)
            .regularTypeface("montserrat.ttf")
            .boldTypeface("montserratbold.ttf")
            .done();
        Fonty.setFonts(activity);
    }

    public static boolean isLogged(Context context) {
        SharedPreferences sp=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getBoolean("logged", false);
    }

    public static String[] getUser(Context context) {
        SharedPreferences sp=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String[] data=new String[3];
        data[0]=""+sp.getString("username", null);
        data[1]=""+sp.getString("number", null);
        data[2]=""+sp.getString("password", null);
        return data;
    }

    public static void saveUser(Context context, String username, String number, String password) {
        SharedPreferences sp=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor spe=sp.edit();
        spe.putBoolean("logged", true);
        spe.putString("username", username);
        spe.putString("number", number);
        spe.putString("password", password);
        spe.apply();
    }

    public static void message(Context context, String title, String message) {
        message(context, title, message, null);
    }

    public static void message(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(""+title)
                .setMessage(""+message)
                .setPositiveButton("ok", listener==null?new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }:listener)
                .create()
                .show();
    }

    public static void confirm(Context context, String title, String message, String option1, DialogInterface.OnClickListener listener1, String option2, DialogInterface.OnClickListener listener2) {
        new AlertDialog.Builder(context)
                .setTitle(""+title)
                .setMessage(""+message)
                .setPositiveButton(option1, listener1==null?new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }:listener1)
                .setNegativeButton(option2, listener2==null?new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }:listener2)
                .create()
                .show();
    }

    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static void startActivityAndFinish(Activity start, Class end, String color, View view) {
        int[] pos=new int[2];
        view.getLocationOnScreen(pos);
        int x=pos[0]+(view.getWidth()/2);
        int y=pos[1]+(view.getHeight()/2);

        startActivityAndFinish(start, end, color, x, y);
    }

    public static void startActivityAndFinish(Activity start, Class end, String color, int x, int y) {
        callingActivity=start;
        nextActivity=end;
        Intent intent=new Intent(start, RevealActivity.class);
        intent.putExtra("color", color);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        start.startActivity(intent);
        start.overridePendingTransition(0, 0);
    }

    public static void startActivityAndFinish(Activity start, String end, String color, View view) {
        int[] pos=new int[2];
        view.getLocationOnScreen(pos);
        int x=pos[0]+(view.getWidth()/2);
        int y=pos[1]+(view.getHeight()/2);

        startActivityAndFinish(start, end, color, x, y);
    }

    public static void startActivityAndFinish(Activity start, String end, String color, int x, int y) {

        log("color: "+color);

        callingActivity=start;
        nextActivity=null;
        Intent intent=new Intent(start, RevealActivity.class);
        intent.putExtra("packageName", end);
        intent.putExtra("color", color);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        start.startActivity(intent);
        start.overridePendingTransition(0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, @ColorRes int color) {
        Window window=activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, color));
    }

    public static String colorToHex(int intColor) {
        return String.format("#%06X", (0xFFFFFF & intColor));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("firstrun", Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("firstrun", true);
    }

    public static void firstRun(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("firstrun", Context.MODE_PRIVATE);
        SharedPreferences.Editor spe=sharedPreferences.edit();

        spe.putBoolean("firstrun", false);
        spe.apply();
    }
}
