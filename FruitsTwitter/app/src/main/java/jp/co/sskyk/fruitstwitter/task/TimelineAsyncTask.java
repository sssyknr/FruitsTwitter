package jp.co.sskyk.fruitstwitter.task;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.AsyncTaskLoader;

import jp.co.sskyk.fruitstwitter.utils.TwitterUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Twitterタイムライン取得
 */
public class TimelineAsyncTask extends AsyncTaskLoader<ResponseList<Status>> {

    private FragmentActivity activity;
    private Paging paging;

    public TimelineAsyncTask(FragmentActivity activity, Paging paging) {
        super(activity);
        this.activity = activity;
        this.paging = paging;
    }

    @Override
    public ResponseList<Status> loadInBackground() {
        return TwitterUtil.getTimelineList(activity, paging);
    }
}
