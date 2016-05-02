package jp.co.sskyk.fruitstwitter.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import jp.co.sskyk.fruitstwitter.Common.Constants;
import jp.co.sskyk.fruitstwitter.R;

/**
 * ビデオ再生アクティビティ
 */
public class VideoActivity extends BaseActivity {
    /** ビデオビュー */
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        setToolbarTitle("Video");

        Intent intent = getIntent();
        if (intent != null) {
            // intentがある
            videoView = (VideoView) findViewById(R.id.activity_image_view_video);
            // 画像を取得
            String path = intent.getStringExtra(Constants.IntentKey.IMAGE_URL);

            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri uri = Uri.parse(path);
            videoView.setMediaController(mc);
            videoView.setVideoURI(uri);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                }
            });
        }
    }
}
