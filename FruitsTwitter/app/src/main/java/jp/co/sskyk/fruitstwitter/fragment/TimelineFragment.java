package jp.co.sskyk.fruitstwitter.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.co.sskyk.fruitstwitter.fragment.adapter.TimelineListAdapter;
import jp.co.sskyk.fruitstwitter.utils.DialogUtil;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * タイムラインFragment
 */
public class TimelineFragment extends BaseListFragment {

    private final TimelineFragment THIS = this;

    private TimelineListAdapter adapter;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = initViews();

        adapter = new TimelineListAdapter(activity);
        pullToRefreshListView.setAdapter(adapter);

        startQuery(ID_TIMELINE_NEW, null, true);

        return view;
    }

    public void onQueryComplete(int id, @NonNull ResponseList<Status> data) {
        switch (id) {
            case ID_TIMELINE_NEW:
                adapter.addTopAll(data);
                break;
            case ID_TIMELINE_OLD:
                adapter.addAll(data);
                break;
            default:
                break;
        }
    }

    public void onPullUpToRefresh() {
        if (!DialogUtil.getShowFlag()) {
            // 通信ダイアログが出ていなければ
            Status status = adapter.getLastItem();
            Paging paging = new Paging();
            if (status != null) {
                paging.setMaxId(status.getId() - 1);    // 指定IDを含んで取得してしまうので-1する。
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_PAGING, paging);
            startQuery(ID_TIMELINE_OLD, bundle, false);
        }
    }

    public void onPullDownToRefresh() {
        if (!DialogUtil.getShowFlag()) {
            Status status = adapter.getFirstItem();
            Paging paging = new Paging();
            if (status != null) {
                paging.setSinceId(status.getId() + 1);  // 指定IDを含んで取得してしまうので+1する。
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_PAGING, paging);
            startQuery(ID_TIMELINE_NEW, bundle, false);
        }
    }
}
