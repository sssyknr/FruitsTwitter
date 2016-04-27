package jp.co.sskyk.fruitstwitter.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import twitter4j.Status;

/**
 * 通知リストadapter
 */
public class NotificationListAdapter extends BaseAdapter {
    /** 表示データ */
    private ArrayList<Status> list = new ArrayList<>();
    /** コンテキスト */
    private Context context;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
