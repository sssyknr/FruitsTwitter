package jp.co.sskyk.fruitstwitter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import jp.co.sskyk.fruitstwitter.R;

/**
 * 通信中に表示するView
 */
public class ProgressView extends FrameLayout implements Animation.AnimationListener {
    /** アニメーション時間 */
    private static final long DURATION = 1500;
    /** 横幅 */
    private float centerWidth;
    /** 縦幅 */
    private float centerHeight;
    /** アニメーション開始角度 */
    private float start = 0f;
    /** アニメーション終了角度 */
    private float end = 180f;
    public ProgressView(Context context) {
        super(context);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.view_progress_image, this);
//        ImageView imageView = (ImageView) view.findViewById(R.id.view_progress_image);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        post(new Runnable() {
            @Override
            public void run() {
                startAnimation(start, end);
            }
        });
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        centerWidth = getWidth() / 2.0f;
        centerHeight = getHeight() / 2.0f;

        /* アニメーション */
        startAnimation(start, end);
    }

    private void startAnimation(float start, float end) {
        Rotate3dAnimation animation = new Rotate3dAnimation(start, end, centerWidth, centerHeight, 0f, true);
        animation.setDuration(DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        startAnimation(animation);
        if (start == 0f) {
            this.start = 180f;
            this.end = 360f;
        } else {
            this.start = 0f;
            this.end = 180f;
        }
    }
}
