package jp.co.sskyk.fruitstwitter.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.fragment.adapter.BaseListFragment;
import jp.co.sskyk.fruitstwitter.fragment.adapter.TimelineListAdapter;
import jp.co.sskyk.fruitstwitter.task.TimelineAsyncTask;
import jp.co.sskyk.fruitstwitter.utils.DialogUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * タイムラインFragment
 */
public class TimelineFragment extends BaseListFragment {

    private final TimelineFragment THIS = this;

    private SwipeRefreshLayout refreshLayout;
    private TimelineListAdapter adapter;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);

        // SwipeRefreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_timeline_refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.strawberry1, R.color.peach1, R.color.pine1, R.color.watermelon1, R.color.melon1, R.color.grape1, R.color.grapefruit1);

        ListView listView = (ListView) view.findViewById(R.id.fragment_timeline_listview);
        adapter = new TimelineListAdapter(activity);

        getLoaderManager().initLoader(1, null, THIS);

        listView.setAdapter(adapter);
        listView.setOnScrollListener(THIS);

        return view;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ResponseList<Status>> loader, ResponseList<Status> data) {
        if (loader.getId() == 1) {
            if (data != null) {
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }
        } else if (loader.getId() == 2) {
            if (data != null) {
                adapter.addTopAll(data);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (!DialogUtil.getShowFlag()) {
            Status status = adapter.getFirstItem();
            Paging paging = new Paging();
            if (status != null) {
                paging.setSinceId(status.getId() + 1);  // 指定IDを含んで取得してしまうので+1する。
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_PAGING, paging);
            getLoaderManager().restartLoader(2, bundle, THIS);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if ((totalItemCount - visibleItemCount) == firstVisibleItem && !DialogUtil.getShowFlag()) {
            // 最下部 or ダイアログ出てない
            Status status = adapter.getLastItem();
            Paging paging = new Paging();
            if (status != null) {
                paging.setMaxId(status.getId() - 1);    // 指定IDを含んで取得してしまうので-1する。
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_PAGING, paging);
            getLoaderManager().restartLoader(1, bundle, THIS);
            Log.d("さささ", "[onLoadFinish] id = " + paging.getMaxId());
        }
    }
}
