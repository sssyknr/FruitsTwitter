package jp.co.sskyk.fruitstwitter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date型操作Utility
 */
public class DateUtil {

    public static String getYMD(Date date) {
        // 現在日時のフォーマットを指定
        return  new SimpleDateFormat("yyyy/MM/dd hh:mm").format(date);
    }
}
