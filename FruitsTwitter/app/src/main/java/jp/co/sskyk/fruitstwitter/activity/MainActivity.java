package jp.co.sskyk.fruitstwitter.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.co.sskyk.fruitstwitter.R;
import jp.co.sskyk.fruitstwitter.fragment.NotificationFragment;
import jp.co.sskyk.fruitstwitter.fragment.TimelineFragment;
import jp.co.sskyk.fruitstwitter.task.AccountInfoAsyncTask;
import twitter4j.User;

/**
 * メインアクティビティ
 */
public class MainActivity extends BaseActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<User> {
    private final MainActivity THIS = this;
    /** ナビゲーションドロワー */
    private DrawerLayout drawerLayout;
    /** アカウント画像 */
    private ImageView accountImage;
    /** アカウント名 */
    private TextView accountName;
    /** アカウントID */
    private TextView accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle("FruitsTwitter");
        initTab();  // タブ作成
        initNavigationView();   // ナビゲーションビュー
        getAccountInfo();   // アカウント情報取得
    }

    /**
     * タブ初期化
     */
    private void initTab() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);

        View view1 = newTabImage(R.drawable.ic_list_black_24dp);
        TabHost.TabSpec spec1 = tabHost.newTabSpec("タブ1").setIndicator(view1);
        tabHost.addTab(spec1, TimelineFragment.class, null);

        View view2 = newTabImage(R.drawable.ic_feedback_black_24dp);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("タブ2").setIndicator(view2);
        tabHost.addTab(spec2, NotificationFragment.class, null);

        View view3 = newTabImage(R.drawable.ic_mail_outline_black_24dp);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("タブ3").setIndicator(view3);
        tabHost.addTab(spec3, TimelineFragment.class, null);

        View view4 = newTabImage(R.drawable.ic_search_black_24dp);
        TabHost.TabSpec spec4 = tabHost.newTabSpec("タブ4").setIndicator(view4);
        tabHost.addTab(spec4, TimelineFragment.class, null);

        View view5 = newTabImage(R.drawable.ic_account_circle_black_24dp);
        TabHost.TabSpec spec5 = tabHost.newTabSpec("タブ5").setIndicator(view5);
        tabHost.addTab(spec5, TimelineFragment.class, null);
    }

    /**
     * ナビゲーションView初期化
     */
    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_navigation_drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
        accountImage = (ImageView) navigationView.findViewById(R.id.drawer_account_image);
        accountName = (TextView) navigationView.findViewById(R.id.drawer_account_name);
        accountId = (TextView) navigationView.findViewById(R.id.drawer_account_id);
        // TODO: 複数アカウント対応
        {

        }
        ImageView menu = (ImageView) findViewById(R.id.toolbar_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    // オープン
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    // クローズ
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    /**
     * タブ作成
     *
     * @param backgroundResource backgroundResource
     * @return View
     */
    private View newTabImage(int backgroundResource) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_image);
        if (backgroundResource != -1) {
            imageView.setImageResource(backgroundResource);
        }
        return view;
    }

    protected void getAccountInfo() {
        getSupportLoaderManager().initLoader(0, null, THIS);
    }

    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        AccountInfoAsyncTask task = new AccountInfoAsyncTask(THIS);
        task.forceLoad();
        return task;
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        Picasso.with(THIS).load(data.getProfileImageURL()).into(accountImage);
        accountName.setText(data.getName());
        accountId.setText("@" + data.getScreenName());
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {

    }
}
