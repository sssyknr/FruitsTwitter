package jp.co.sskyk.fruitstwitter.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.co.sskyk.fruitstwitter.Common.Constants;
import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.activity.ImageViewActivity;
import jp.co.sskyk.fruitstwitter.activity.VideoActivity;
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
        final ViewHolder viewHolder;
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
        viewHolder.imageContainer.removeAllViews();
        for (final ExtendedMediaEntity entity : mediaEntities) {
            if (Constants.MediaEntityType.TYPE_PHOTO.equals(entity.getType())) {
                // 画像
                setImage(viewHolder, entity);
            } else {
                // 動画
                setVideo(viewHolder, entity);
            }
            tmp = tmp.replace(entity.getText(), "");
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
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public Status getLastItem() {
        int last = getCount();
        if (last == 0) {
            return null;
        }
        return list.get(last - 1);
    }

    /**
     * 静止画
     */
    private void setImage(final ViewHolder viewHolder, final ExtendedMediaEntity entity) {
        View view = View.inflate(context, R.layout.inflate_timeline_image, viewHolder.imageContainer);

        // サムネイル
        ImageView imageView = (ImageView) view.findViewById(R.id.adapter_timeline_upload_image);
        Picasso.with(context).load(entity.getMediaURL()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = entity.getMediaURL();
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putExtra(Constants.IntentKey.IMAGE_URL, url);
                context.startActivity(intent);
            }
        });

        // 再生マークアイコン
        ImageView playImage = (ImageView) view.findViewById(R.id.adapter_timeline_upload_image_play);
        playImage.setVisibility(View.GONE);

    }

    private void setVideo(final ViewHolder viewHolder, final ExtendedMediaEntity entity) {
        View view = View.inflate(context, R.layout.inflate_timeline_image, viewHolder.imageContainer);
        // サムネイル
        ImageView imageView = (ImageView) view.findViewById(R.id.adapter_timeline_upload_image);
        Picasso.with(context).load(entity.getMediaURL()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "";
                for (ExtendedMediaEntity.Variant variant : entity.getVideoVariants()) {
                    url = variant.getUrl();
                }
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra(Constants.IntentKey.IMAGE_URL, url);
                context.startActivity(intent);
            }
        });

        // 再生マークアイコン
        ImageView playImage = (ImageView) view.findViewById(R.id.adapter_timeline_upload_image_play);
        playImage.setVisibility(View.VISIBLE);
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
