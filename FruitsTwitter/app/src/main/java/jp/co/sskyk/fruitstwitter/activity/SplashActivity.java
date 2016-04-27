package jp.co.sskyk.fruitstwitter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.Window;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.utils.TwitterUtil;

/**
 * スプラッシュ画面
 */
public class SplashActivity extends BaseActivity {
    /**
     * 自インスタンス
     */
    private final SplashActivity THIS = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        // タイトルを非表示
        setContentView(R.layout.activity_splash);
        Handler hdl = new Handler();
        if (TwitterUtil.hasAccessToken(THIS)) {
            // アクセストークンあり
            hdl.postDelayed(new mainHandler(), 1500);
        } else {
            // アクセストークンなし
            hdl.postDelayed(new authHandler(), 1500);
        }
    }

    /**
     * 1秒後メインアクティビティに遷移
     */
    class mainHandler implements Runnable {
        public void run() {
            // スプラッシュ完了後に実行するActivityを指定
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            // SplashActivityを終了
            SplashActivity.this.finish();
        }
    }

    /**
     * 1秒後認証アクティビティに遷移
     */
    class authHandler implements Runnable {
        public void run() {
            // スプラッシュ完了後に実行するActivityを指定
            Intent intent = new Intent(getApplication(), TwitterAuthActivity.class);
            startActivity(intent);
            // SplashActivityを終了
            SplashActivity.this.finish();
        }
    }
}