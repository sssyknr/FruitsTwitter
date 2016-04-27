package jp.co.sskyk.fruitstwitter.fragment.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

import jp.co.sskyk.fruitstwitter.task.NotificationAsyncTask;
import jp.co.sskyk.fruitstwitter.task.TimelineAsyncTask;
import jp.co.sskyk.fruitstwitter.utils.DialogUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * リスト表示系フラグメントの基盤クラス
 */
public class BaseListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ResponseList<Status>>,
        SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener {

    /** キー */
    private static final String KEY_PAGING = "KEY_PAGING";

    protected static final int ID_TIMELINE_NEW = 0;
    protected static final int ID_TIMELINE_OLD = 1;
    protected static final int ID_NOTIFICATION_NEW = 2;
    protected static final int ID_NOTIFICATION_OLD = 3;

    protected FragmentActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentActivity) {
            this.activity = (FragmentActivity) activity;
        }
    }

    @Override
    public Loader<ResponseList<Status>> onCreateLoader(int id, Bundle args) {
        DialogUtil.showProgressDialog(activity);
        Paging paging = null;
        if (args != null) {
            // 追加読み込みの場合
            paging = (Paging) args.getSerializable(KEY_PAGING);
        }
        switch (id) {
            case ID_TIMELINE_NEW:
            case ID_TIMELINE_OLD:
                TimelineAsyncTask task = new TimelineAsyncTask(activity, paging);
                task.forceLoad();
                return task;

            case ID_NOTIFICATION_NEW:
            case ID_NOTIFICATION_OLD:
                NotificationAsyncTask task1 = new NotificationAsyncTask(activity, paging);
                task1.forceLoad();
                return task1;

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<ResponseList<Status>> loader, ResponseList<Status> data) {
        switch (loader.getId()) {
            case ID_TIMELINE_NEW:
            case ID_TIMELINE_OLD:

            case ID_NOTIFICATION_NEW:
            case ID_NOTIFICATION_OLD:


            default:
                break;
        }
        DialogUtil.dismissProgressDialog(activity);
    }

    @Override
    public void onLoaderReset(Loader<ResponseList<Status>> loader) {
        DialogUtil.dismissProgressDialog(activity);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
