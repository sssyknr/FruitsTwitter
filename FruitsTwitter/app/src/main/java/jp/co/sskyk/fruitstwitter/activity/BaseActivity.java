package jp.co.sskyk.fruitstwitter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import jp.co.sskyk.fruitstwitter.Common.Constants;
import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.utils.PreferenceUtil;
import jp.co.sskyk.fruitstwitter.utils.ThemeUtil;

/**
 * アクティビティ基底クラス
 */
public class BaseActivity extends FragmentActivity {
    /** 自インスタンス */
    private final BaseActivity THIS = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // テーマ変更
        int theme = PreferenceUtil.readInt(THIS, Constants.PreferenceKey.THEME);
        setTheme(ThemeUtil.getTheme(theme));

        super.onCreate(savedInstanceState);
        // 画面縦固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * ツールバーのタイトルを設定
     *
     * @param title タイトル
     */
    protected void setToolbarTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(title);
    }
}
