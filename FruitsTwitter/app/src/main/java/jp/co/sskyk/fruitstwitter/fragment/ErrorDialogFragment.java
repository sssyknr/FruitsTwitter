package jp.co.sskyk.fruitstwitter.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import jp.co.sskyk.fruitstwitter.utils.DialogUtil;

/**
 * エラーダイアログフラグメント
 */
public class ErrorDialogFragment extends DialogFragment {
    /**
     * MyDialog で何か処理が起こった場合にコールバックされるリスナ.
     */
    public interface Callback {

        /**
         * MyDialog で positiveButton, NegativeButton, リスト選択など行われた際に呼ばれる.
         *
         * @param requestCode MyDialogFragment 実行時 requestCode
         * @param resultCode  DialogInterface.BUTTON_(POSI|NEGA)TIVE 若しくはリストの position
         * @param params      MyDialogFragment に受渡した引数
         */
        void onMyDialogSucceeded(int requestCode, int resultCode, Bundle params);

        /**
         * MyDialog がキャンセルされた時に呼ばれる.
         *
         * @param requestCode MyDialogFragment 実行時 requestCode
         * @param params      MyDialogFragment に受渡した引数
         */
        void onMyDialogCancelled(int requestCode, Bundle params);
    }

    /**
     * MyDialogFragment を Builder パターンで生成する為のクラス.
     */
    public static class Builder {

        /**
         * Activity.
         */
        final FragmentActivity mActivity;

        /**
         * 親 Fragment.
         */
        final Fragment mParentFragment;

        /**
         * タイトル.
         */
        String mTitle;

        /**
         * メッセージ.
         */
        String mMessage;

        /**
         * 選択リスト.
         */
        String[] mItems;

        /**
         * 肯定ボタン.
         */
        String mPositiveLabel;

        /**
         * 否定ボタン.
         */
        String mNegativeLabel;

        /**
         * リクエストコード. 親 Fragment 側の戻りで受け取る.
         */
        int mRequestCode = -1;

        /**
         * リスナに受け渡す任意のパラメータ.
         */
        Bundle mParams;

        /**
         * DialogFragment のタグ.
         */
        String mTag = "default";

        /**
         * Dialog をキャンセル可かどうか.
         */
        boolean mCancelable = true;

        /**
         * コンストラクタ. Activity 上から生成する場合.
         *
         * @param activity FragmentActivity
         */
        public <A extends FragmentActivity> Builder(@NonNull final A activity) {
            mActivity = activity;
            mParentFragment = null;
        }

        /**
         * コンストラクタ. Fragment 上から生成する場合.
         *
         * @param parentFragment 親 Fragment
         */
        public <F extends Fragment> Builder(@NonNull final F parentFragment) {
            mParentFragment = parentFragment;
            mActivity = null;
        }

        /**
         * タイトルを設定する.
         *
         * @param title タイトル
         * @return Builder
         */
        public Builder title(@NonNull final String title) {
            mTitle = title;
            return this;
        }

        /**
         * タイトルを設定する.
         *
         * @param title タイトル
         * @return Builder
         */
        public Builder title(@StringRes final int title) {
            return title(getContext().getString(title));
        }

        /**
         * メッセージを設定する.
         *
         * @param message メッセージ
         * @return Builder
         */
        public Builder message(@NonNull final String message) {
            mMessage = message;
            return this;
        }

        /**
         * メッセージを設定する.
         *
         * @param message メッセージ
         * @return Builder
         */
        public Builder message(@StringRes final int message) {
            return message(getContext().getString(message));
        }

        /**
         * 選択リストを設定する.
         *
         * @param items 選択リスト
         * @return Builder
         */
        public Builder items(@NonNull final String... items) {
            mItems = items;
            return this;
        }

        /**
         * 肯定ボタンを設定する.
         *
         * @param positiveLabel 肯定ボタンのラベル
         * @return Builder
         */
        public Builder positive(@NonNull final String positiveLabel) {
            mPositiveLabel = positiveLabel;
            return this;
        }

        /**
         * 肯定ボタンを設定する.
         *
         * @param positiveLabel 肯定ボタンのラベル
         * @return Builder
         */
        public Builder positive(@StringRes final int positiveLabel) {
            return positive(getContext().getString(positiveLabel));
        }

        /**
         * 否定ボタンを設定する.
         *
         * @param negativeLabel 否定ボタンのラベル
         * @return Builder
         */
        public Builder negative(@NonNull final String negativeLabel) {
            mNegativeLabel = negativeLabel;
            return this;
        }

        /**
         * 否定ボタンを設定する.
         *
         * @param negativeLabel 否定ボタンのラベル
         * @return Builder
         */
        public Builder negative(@StringRes final int negativeLabel) {
            return negative(getContext().getString(negativeLabel));
        }

        /**
         * リクエストコードを設定する.
         *
         * @param requestCode リクエストコード
         * @return Builder
         */
        public Builder requestCode(final int requestCode) {
            mRequestCode = requestCode;
            return this;
        }

        /**
         * DialogFragment のタグを設定する.
         *
         * @param tag タグ
         * @return Builder
         */
        public Builder tag(final String tag) {
            mTag = tag;
            return this;
        }

        /**
         * Positive / Negative 押下時のリスナに受け渡すパラメータを設定する.
         *
         * @param params リスナに受け渡すパラメータ
         * @return Builder
         */
        public Builder params(final Bundle params) {
            mParams = new Bundle(params);
            return this;
        }

        /**
         * Dialog をキャンセルできるか否かをセットする.
         *
         * @param cancelable キャンセル可か否か
         * @return Builder
         */
        public Builder cancelable(final boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        /**
         * DialogFragment を Builder に設定した情報を元に show する.
         */
        public void show() {
            final Bundle args = new Bundle();
            args.putString("title", mTitle);
            args.putString("message", mMessage);
            args.putStringArray("items", mItems);
            args.putString("positive_label", mPositiveLabel);
            args.putString("negative_label", mNegativeLabel);
            args.putBoolean("cancelable", mCancelable);
            if (mParams != null) {
                args.putBundle("params", mParams);
            }

            final ErrorDialogFragment f = new ErrorDialogFragment();
            if (mParentFragment != null) {
                f.setTargetFragment(mParentFragment, mRequestCode);
            } else {
                args.putInt("request_code", mRequestCode);
            }
            f.setArguments(args);
            if (mParentFragment != null) {
                f.show(mParentFragment.getChildFragmentManager(), mTag);
            } else {
                f.show(mActivity.getSupportFragmentManager(), mTag);
            }
        }

        /**
         * コンテキストを取得する. getString() 呼び出しの為.
         *
         * @return Context
         */
        private Context getContext() {
            return (mActivity == null ? mParentFragment.getActivity() : mActivity).getApplicationContext();
        }
    }

    /**
     * Callback.
     */
    private Callback mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Object callback = getParentFragment();
        if (callback == null) {
            callback = getActivity();
            if (callback != null && callback instanceof Callback) {
                mCallback = (Callback) callback;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                if (mCallback != null) {
                    mCallback.onMyDialogSucceeded(getRequestCode(), which, getArguments().getBundle("params"));
                }
            }
        };
        final String title = getArguments().getString("title");
        final String message = getArguments().getString("message");
        final String[] items = getArguments().getStringArray("items");
        final String positiveLabel = getArguments().getString("positive_label");
        final String negativeLabel = getArguments().getString("negative_label");
        setCancelable(getArguments().getBoolean("cancelable"));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (items != null && items.length > 0) {
            builder.setItems(items, listener);
        }
        if (!TextUtils.isEmpty(positiveLabel)) {
            builder.setPositiveButton(positiveLabel, listener);
        }
        if (!TextUtils.isEmpty(negativeLabel)) {
            builder.setNegativeButton(negativeLabel, listener);
        }
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mCallback != null) {
            mCallback.onMyDialogCancelled(getRequestCode(), getArguments().getBundle("params"));
        }
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

    /**
     * リクエストコードを取得する. Activity と ParentFragment 双方に対応するため.
     *
     * @return requestCode
     */
    private int getRequestCode() {
        return getArguments().containsKey("request_code") ? getArguments().getInt("request_code") : getTargetRequestCode();
    }
}
