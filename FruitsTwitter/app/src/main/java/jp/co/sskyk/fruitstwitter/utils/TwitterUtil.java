package jp.co.sskyk.fruitstwitter.utils;

import jp.co.sskyk.fruitstwitter.R;
import twitter4j.AccountSettings;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TwitterUtil {

    private static final String TOKEN = "token";
    private static final String TOKEN_SECRET = "token_secret";
    private static final String PREF_NAME = "twitter_access_token";

    /**
     * Twitterインスタンスを取得します。アクセストークンが保存されていれば自動的にセットします。
     *
     * @param context Context
     * @return Twitter
     */
    public static Twitter getTwitterInstance(Context context) {
        String consumerKey = context.getString(R.string.twitter_consumer_key);
        String consumerSecret = context.getString(R.string.twitter_consumer_secret);

        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        if (hasAccessToken(context)) {
            twitter.setOAuthAccessToken(loadAccessToken(context));
        }
        return twitter;
    }

    /**
     * アクセストークンをプリファレンスに保存します。
     *
     * @param context Context
     * @param accessToken AccessToken
     */
    public static void storeAccessToken(Context context, AccessToken accessToken) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(TOKEN, accessToken.getToken());
        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
        editor.apply();
    }

    /**
     * アクセストークンをプリファレンスから読み込みます。
     *
     * @param context Context
     * @return AccessToken
     */
    public static AccessToken loadAccessToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        String tokenSecret = preferences.getString(TOKEN_SECRET, null);
        if (token != null && tokenSecret != null) {
            return new AccessToken(token, tokenSecret);
        } else {
            return null;
        }
    }

    /**
     * アクセストークンが存在する場合はtrueを返します。
     *
     * @return boolean
     */
    public static boolean hasAccessToken(Context context) {
        return loadAccessToken(context) != null;
    }

    /**
     * タイムラインリストの取得
     *
     * @param activity FragmentActivity
     * @return ResponseList<Status>
     */
    public static ResponseList<Status> getTimelineList(FragmentActivity activity, Paging paging) {
        ResponseList<Status> homeTimeline = null;
        try {
            //TLの取得
            Twitter twitter = getTwitterInstance(activity);
            if (paging == null) {
                paging = new Paging();
            }
            paging.setCount(50);
            homeTimeline = twitter.getHomeTimeline(paging);

        } catch (TwitterException e) {
            e.printStackTrace();
            if(e.isCausedByNetworkIssue()){
                DialogUtil.showErrorDialog(activity, "ネットワークエラー", "通信状態の良い環境で再度お試しください。", 0, null, "OK", null);
            }else{
                DialogUtil.showErrorDialog(activity, "エラー", e.getMessage(), 0, null, "OK", null);
            }
        }
        return homeTimeline;
    }

    /**
     * ユーザー情報を取得
     *
     * @param context コンテキスト
     * @return User
     */
    public static User getAccountInfo(Context context) {
        Twitter twitter = getTwitterInstance(context);
        User user = null;
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * メンション付きtweetの日取得
     *
     * @param context コンテキスト
     * @param paging Paging
     * @return ResponseList<Status>
     */
    public static ResponseList<Status> getNotification(Context context, Paging paging) {
        Twitter twitter = getTwitterInstance(context);
        ResponseList<Status> list = null;
        if (paging == null) {
            paging = new Paging();
        }
        paging.setCount(50);
        try {
            list = twitter.getMentionsTimeline(paging);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return list;
    }
}