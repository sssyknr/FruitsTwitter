package jp.co.sskyk.fruitstwitter;

import android.support.annotation.NonNull;

import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * ListFragmentInterface
 */
abstract class ListFragmentInterface {
    /** DB操作終了時コールバック */
    abstract void onQueryComplete(int id, @NonNull ResponseList<Status> data);
    /** リストリフレッシュ時コールバック */
    abstract void onPullUpToRefresh();
    /** リスト最下層コールバック */
    abstract void onPullDownToRefresh();
}
