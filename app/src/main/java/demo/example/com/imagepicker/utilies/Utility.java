package demo.example.com.imagepicker.utilies;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;



import java.io.IOException;
import java.io.InputStream;

import demo.example.com.imagepicker.R;

public class Utility {

    public Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(),
                    Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }



    public static Bitmap getBitmapFromdCard(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    public SpannableStringBuilder getSpannableString(Context mContext, String value, int resourceId ){
        String s = " ";
        int position = 1;

        SpannableStringBuilder text = new SpannableStringBuilder();
        Drawable d = mContext.getResources().getDrawable(
                resourceId);
        Resources resource = mContext.getResources();
        DisplayMetrics metrics = resource
                .getDisplayMetrics();
        int right = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, resource.getDimension(R.dimen.button_text_size), metrics);
        int bottom = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, resource.getDimension(R.dimen.button_text_size), metrics);
        d.setBounds(0, 0, right, bottom);
        String contentBetweenTags = "";
        SpannableStringBuilder sb1 = new SpannableStringBuilder();
        sb1.append(s + contentBetweenTags);
        text.append(s + contentBetweenTags);
        for (int i = 0; i < position; i++) {
            text.append(s + contentBetweenTags);
            text.setSpan(new ImageSpan(d), text.length() - s.length()
                            - contentBetweenTags.length(), text.length()
                            - contentBetweenTags.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text.append(s);
        text.append(value);

        return text ;
    }

    /**
     * To hide the soft key pad if open
     *
     */
    public  void hideSoftKeypad(Context context) {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }

    /**
     * To hide the soft key pad if open
     *
     */
    public void hideSoftKeypadFromDialog(Context context, AlertDialog dialog) {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (dialog.getWindow() != null) {
            imm.hideSoftInputFromWindow(dialog.getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }



    public static boolean isDeviceOnline(Context context) {

        boolean isConnectionAvail = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo.isConnected();
        } catch (Exception e) {
        }
        return isConnectionAvail;
    }


}
