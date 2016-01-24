package com.lovejjfg.indexdemo;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static Toast sToast;

    public static void showToast(Context mContext, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
