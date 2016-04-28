package jp.co.sskyk.fruitstwitter.fragment.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.task.NotificationAsyncTask;
import jp.co.sskyk.fruitstwitter.task.TimelineAsyncTask;
import jp.co.sskyk.fruitstwitter.utils.DialogUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * リスト表示系フラグメントの基盤クラス
 */
public abstract class BaseListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ResponseList<Status>>,
        PullToRefreshBase.OnRefreshListener2 {

    /** キー */
    protected static final String KEY_PAGING = "KEY_PAGING";

    protected static final int ID_TIMELINE_NEW = 0;
    protected static final int ID_TIMELINE_OLD = 1;
    protected static final int ID_NOTIFICATION_NEW = 2;
    protected static final int ID_NOTIFICATION_OLD = 3;

    protected FragmentActivity activity;
    protected PullToRefreshListView pullToRefreshListView;

    // ///////////////////////////////////////////
    // Override Method
    // ///////////////////////////////////////////
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentActivity) {
            this.activity = (FragmentActivity) activity;
        }
    }

    @Override
    public Loader<ResponseList<Status>> onCreateLoader(int id, Bundle args) {
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
        if (data != null) {
            onQueryComplete(loader.getId(), data);
        }
        DialogUtil.dismissProgressDialog(activity);
    }

    @Override
    public void onLoaderReset(Loader<ResponseList<Status>> loader) {
        DialogUtil.dismissProgressDialog(activity);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        onPullDownToRefresh();
        new FinishRefresh().execute();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        onPullUpToRefresh();
        new FinishRefresh().execute();
    }

    // ///////////////////////////////////////////
    // Protected Method
    // ///////////////////////////////////////////
    /**
     * 共通Viewの初期化
     */
    protected View initViews() {
        View view = View.inflate(getActivity(), R.layout.fragment_base_list, null);

        // 引っ張って更新リスト
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.fragment_base_list_listview);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);

        return view;
    }

    /**
     * DB操作開始
     *
     * @param id ID
     * @param bundle Bundle
     * @param isProgressDialog プログレス有無
     */
    protected void startQuery(int id, Bundle bundle, boolean isProgressDialog) {
        if (DialogUtil.getShowFlag()) {
            // すでに他通信中の場合、通信しない
            return;
        }
        if (isProgressDialog) {
            // プログレスダイアログ表示
            DialogUtil.showProgressDialog(activity);
        }
        // DB操作開始
        getLoaderManager().restartLoader(id, bundle, this);
    }
    // ////////////////////////////////////
    // Abstract Method
    // /////////////////////////////////////
    /** DB操作終了時コールバック */
    protected abstract void onQueryComplete(int id, @NonNull ResponseList<Status> data);
    /** リスト下部更新 */
    protected abstract void onPullUpToRefresh();
    /** リスト上部更新 */
    protected abstract void onPullDownToRefresh();

    // ///////////////////////////////////////////
    // Private Class
    // ///////////////////////////////////////////
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 更新アニメーション終了
            pullToRefreshListView.onRefreshComplete();
        }
    }
}
