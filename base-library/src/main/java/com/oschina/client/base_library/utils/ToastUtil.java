package com.oschina.client.base_library.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static final int SHORT = 0;
    private static final int LONG = 1;
    private static Toast toast = null;

    public static void toast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void toast(Context context, String text, int time) {
        if (time == SHORT) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

        } else if (time == LONG) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

        }
    }
}
