package jp.co.sskyk.fruitstwitter.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.co.sskyk.fruitstwitter.fragment.adapter.BaseListFragment;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * 通知Fragment
 */
public class NotificationFragment extends BaseListFragment {
    private final NotificationFragment THIS = this;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return initViews();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(1, null, THIS);
    }

    @Override
    protected void onQueryComplete(int id, @NonNull ResponseList<Status> data) {

    }

    @Override
    protected void onPullUpToRefresh() {

    }

    @Override
    protected void onPullDownToRefresh() {

    }
}
