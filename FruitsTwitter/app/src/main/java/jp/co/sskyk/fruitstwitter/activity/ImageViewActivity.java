package jp.co.sskyk.fruitstwitter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jp.co.sskyk.fruitstwitter.Common.Constants;
import jp.co.sskyk.fruitstwitter.R;

/**
 * 画像表示アクティビティ
 */
public class ImageViewActivity extends BaseActivity {
    /** メニューID */
    private static final int MENU_SAVE = 0;

    /** イメージビュー */
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        setToolbarTitle("Image");

        Intent intent = getIntent();
        if (intent != null) {
            // intentがある
            imageView = (ImageView) findViewById(R.id.activity_image_view_image);
            // 画像を取得
            Picasso.with(this).load(intent.getStringExtra(Constants.IntentKey.IMAGE_URL)).into(imageView);
            // imageView長押しで画像保存
            registerForContextMenu(imageView);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //コンテキストメニューの設定
        menu.setHeaderTitle("メニュー");
        //Menu.add(int groupId, int itemId, int order, CharSequence title)
        menu.add(0, MENU_SAVE, 0, "保存");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SAVE:
                // 保存ボタン
                writeImage();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * 画像を保存
     */
    private void writeImage() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/FruitsTwitter";
        File file = new File(path);

        // 保存先パス確認
        if (!file.exists()) {
            // なければ作成
            if (!file.mkdir()) {
                Log.d("Debug", "Make Dir Error");
            }
        }

        // 画像保存パス
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String imgPath = file.toString() + "/" + sf.format(cal.getTime()) + ".png";

        try {
            FileOutputStream out = new FileOutputStream(imgPath);
            imageView.setDrawingCacheEnabled(true);
            Bitmap bmp = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); //PNG保存
            out.close();
            String[] mimeTypes = {"image/png"};
            MediaScannerConnection.scanFile(this, new String[]{imgPath}, mimeTypes, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
