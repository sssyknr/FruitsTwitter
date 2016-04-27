package jp.co.sskyk.fruitstwitter.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import jp.co.sskyk.fruitstwitter.fragment.ErrorDialogFragment;
import jp.co.sskyk.fruitstwitter.fragment.ProgressDialogFragment;

/**
 * ダイアログUtility
 */
public class DialogUtil {
    /** ダイアログ表示中フラグ */
    private static boolean isShow;

    public static <T extends FragmentActivity> void showErrorDialog(T activity, String title, String message, int requestCode, Bundle params, String positive, String negative) {
        if (isShow) {
            // すでに表示中の場合
            ProgressDialogFragment fragment = (ProgressDialogFragment) activity.getSupportFragmentManager().findFragmentByTag(ProgressDialogFragment.TAG);
            if (fragment == null) {
                return;
            } else if (fragment.getDialog() != null) {
                // プログレスダイアログ表示中はエラーダイアログ優先
                fragment.getDialog().dismiss();
            }
        }
        ErrorDialogFragment.Builder builder = new ErrorDialogFragment.Builder(activity)
                .title(title)
                .message(message)
                .requestCode(requestCode);
        if (params != null) {
            builder.params(params);
        }
        if (positive != null) {
            builder.positive(positive);
        }
        if (negative != null) {
            builder.negative(negative);
        }
        builder.show();
    }

    public static void showProgressDialog(FragmentActivity activity) {
        if (isShow) {
            // すでに表示中の場合
            return;
        }
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.show(activity.getSupportFragmentManager(), ProgressDialogFragment.TAG);
    }

    public static void dismissProgressDialog(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        ProgressDialogFragment fragment = (ProgressDialogFragment) manager.findFragmentByTag(ProgressDialogFragment.TAG);
        if (fragment != null && fragment.getDialog() != null) {
            fragment.getDialog().dismiss();
        }
    }

    public static void setShowFlag(boolean flag) {
        isShow = flag;
    }

    public static boolean getShowFlag() {
        return isShow;
    }
}
