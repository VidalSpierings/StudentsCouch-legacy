package com.studentscouch.projectbostonfiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class DeepCode {

    @SuppressLint("StaticFieldLeak")
    private static Activity activity;

    DeepCode(Activity activity){
        DeepCode.activity =activity;
    }

public static String BitmapToString (Bitmap bitmap){

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
    byte[] b = baos.toByteArray();

    // convert Bitmap object to byte array object

    return  Base64.encodeToString(b, Base64.DEFAULT);

    // convert byte array object to (Base64 encoded) String object

}

public static Uri StringToUri (Context context, String fileName) {

    ContentValues values = new ContentValues();
    values.put(MediaStore.Images.Media.TITLE, fileName);

    return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

}

    public static String toMoneySum(double value) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        return formatter.format(value);

    }

}
