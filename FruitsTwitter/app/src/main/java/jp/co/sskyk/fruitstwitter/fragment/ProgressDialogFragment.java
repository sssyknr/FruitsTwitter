package jp.co.sskyk.fruitstwitter.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.utils.DialogUtil;

/**
 * 通信中ダイアログ
 */
public class ProgressDialogFragment extends DialogFragment {
    public static final String TAG = "jp.co.sskyk.fruitstwitter.fragment.ProgressDialogFragment";
    private FragmentActivity activity;

    public ProgressDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentActivity) {
            this.activity = (FragmentActivity) activity;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(activity, R.layout.fragment_progress_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.ProgressDialog);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (getDialog() != null) {
            getDialog().dismiss();
        }
        DialogUtil.setShowFlag(false);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        DialogUtil.setShowFlag(true);
        super.show(manager, tag);
    }
}
