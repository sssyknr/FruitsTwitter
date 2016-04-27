package jp.co.sskyk.fruitstwitter.fragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.utils.DateUtil;
import twitter4j.ExtendedMediaEntity;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;

/**
 * タイムライン用アダプター
 */
public class TimelineListAdapter extends BaseAdapter {
    /** 表示データ */
    private ArrayList<Status> list = new ArrayList<>();
    /** コンテキスト */
    private Context context;

    public TimelineListAdapter(Context context) {
        this.context = context;
    }

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
        Status item = (Status) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_timeline, null);
            viewHolder = new ViewHolder();
            viewHolder.accountIdText = (TextView) convertView.findViewById(R.id.adapter_timeline_account_id);
            viewHolder.accountImage = (ImageView) convertView.findViewById(R.id.adapter_timeline_account_image);
            viewHolder.accountText = (TextView) convertView.findViewById(R.id.adapter_timeline_account_name);
            viewHolder.createAtText = (TextView) convertView.findViewById(R.id.adapter_timeline_create_at);
            viewHolder.favoriteCountText = (TextView) convertView.findViewById(R.id.adapter_timeline_favorite);
            viewHolder.retweetCountText = (TextView) convertView.findViewById(R.id.adapter_timeline_retweet);
            viewHolder.tweetText = (TextView) convertView.findViewById(R.id.adapter_timeline_message);
            viewHolder.imageContainer = (LinearLayout) convertView.findViewById(R.id.adapter_timeline_image_container);
            viewHolder.retweetAlertText = (TextView) convertView.findViewById(R.id.adapter_timeline_retweet_alert);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (item.getRetweetedStatus() != null) {
            // リツイートだった場合、リツイート元のデータを表示
            viewHolder.retweetAlertText.setVisibility(View.VISIBLE);
            viewHolder.retweetAlertText.setText(item.getUser().getName() + "さんがリツイートしました");
            item = item.getRetweetedStatus();
        } else {
            viewHolder.retweetAlertText.setVisibility(View.GONE);
        }
        viewHolder.accountText.setText(item.getUser().getName());
        viewHolder.accountIdText.setText("@" + item.getUser().getScreenName());
        Picasso.with(context).load(item.getUser().getProfileImageURL()).into(viewHolder.accountImage);
        viewHolder.favoriteCountText.setText(String.valueOf(item.getFavoriteCount()));
        viewHolder.retweetCountText.setText(String.valueOf(item.getRetweetCount()));
        viewHolder.createAtText.setText(DateUtil.getYMD(item.getCreatedAt()));

        // 画像表示・URL表示削除
        ExtendedMediaEntity[] mediaEntities = item.getExtendedMediaEntities();
        String tmp = item.getText();
        int imageCount = 1;
        LinearLayout layout = null;
        viewHolder.imageContainer.removeAllViews();
        for (ExtendedMediaEntity entity : mediaEntities) {
            if (imageCount == 1) {
                // 2の倍数で新しいレイアウト
                // TODO: 複数画像があるとき
                layout = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setLayoutParams(params);
                viewHolder.imageContainer.addView(layout);
                imageCount = 1 - imageCount;
            }
            tmp = tmp.replace(entity.getText(), "");
            ImageView imageView = (ImageView) View.inflate(context, R.layout.inflate_timeline_image, layout).findViewById(R.id.adapter_timeline_upload_image);
            Picasso.with(context).load(entity.getMediaURL()).into(imageView);
        }
        // URL置き換え
        URLEntity[] urlEntities = item.getURLEntities();
        for (URLEntity entity : urlEntities) {
            tmp = tmp.replace(entity.getText(), entity.getDisplayURL());
        }
        viewHolder.tweetText.setText(tmp);

        return convertView;
    }

    public void addTopAll(ResponseList<Status> items) {
        // リストの先頭に追加
        ArrayList<Status> tmp = new ArrayList<>();
        tmp.addAll(items);
        tmp.addAll(list);
        list = null;
        list = tmp;
        notifyDataSetChanged();
    }

    public void addAll(ResponseList<Status> items) {
        // リストに追加
        list.addAll(items);
        notifyDataSetChanged();
    }

    public Status getFirstItem() {
        return list.get(0);
    }

    public Status getLastItem() {
        int last = getCount();
        if (last == 0) {
            return null;
        }
        return list.get(last - 1);
    }

    public void removeFirstItem() {
        list.remove(0);
    }

    public void removeLastItem() {
        int last = getCount();
        if (last != 0) {
            list.remove(last - 1);
        }
    }

    private class ViewHolder {
        ImageView accountImage;
        TextView accountText;
        TextView accountIdText;
        TextView tweetText;
        TextView createAtText;
        TextView favoriteCountText;
        TextView retweetCountText;
        LinearLayout imageContainer;
        TextView retweetAlertText;
    }
}
