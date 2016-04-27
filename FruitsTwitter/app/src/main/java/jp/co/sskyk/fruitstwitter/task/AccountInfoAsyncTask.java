package jp.co.sskyk.fruitstwitter.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import jp.co.sskyk.fruitstwitter.utils.TwitterUtil;
import twitter4j.User;

/**
 * アカウント情報取得
 */
public class AccountInfoAsyncTask extends AsyncTaskLoader<User> {
    private Context context;

    public AccountInfoAsyncTask(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public User loadInBackground() {
        return TwitterUtil.getAccountInfo(context);
    }
}
