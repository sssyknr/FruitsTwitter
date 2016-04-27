package jp.co.sskyk.fruitstwitter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jp.co.sskyk.fruitstwitter.R;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * 通知Fragment
 */
public class NotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<ResponseList<Status>> {
    private final NotificationFragment THIS = this;

    private FragmentActivity activity;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentActivity) {
            this.activity = (FragmentActivity) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_notification_refresh);
        refreshLayout.setOnRefreshListener(THIS);

        ListView listView = (ListView) view.findViewById(R.id.fragment_notification_listview);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(1, null, THIS);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public Loader<ResponseList<Status>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ResponseList<Status>> loader, ResponseList<Status> data) {

    }

    @Override
    public void onLoaderReset(Loader<ResponseList<Status>> loader) {

    }
}
