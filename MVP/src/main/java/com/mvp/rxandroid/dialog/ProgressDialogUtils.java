package com.mvp.rxandroid.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mvp.rxandroid.R;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class ProgressDialogUtils {

    private static ProgressDialog mDialog;

    public static void showProgress(Context mContext)
    {
        synchronized (ProgressDialogUtils.class)
        {
            if (mDialog == null)
            {
                mDialog = new ProgressDialog(mContext, R.style.CustomProgressDialog);
                mDialog.setCancelable(true);
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        mDialog = null;
                    }
                });
                mDialog.show();
                mDialog.setContentView(R.layout.dialog_progress);
            }
        }
    }

    public static void dismissProgress(Activity mContext)
    {
        synchronized (ProgressDialogUtils.class)
        {
            if (mDialog != null)
            {
                if (!mContext.isDestroyed())
                {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        }
    }

}
