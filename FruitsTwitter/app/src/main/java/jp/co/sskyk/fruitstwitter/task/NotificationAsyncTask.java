package jp.co.sskyk.fruitstwitter.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import jp.co.sskyk.fruitstwitter.utils.TwitterUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * メンションを取得
 * TODO: リツイート・お気に入り・フォロー系
 */
public class NotificationAsyncTask extends AsyncTaskLoader<ResponseList<Status>> {
    /** コンテキスト */
    private Context context;
    private Paging paging;

    public NotificationAsyncTask(Context context, Paging paging) {
        super(context);
        this.context = context;
        this.paging = paging;
    }

    @Override
    public ResponseList<Status> loadInBackground() {
        return TwitterUtil.getNotification(context, paging);
    }
}
