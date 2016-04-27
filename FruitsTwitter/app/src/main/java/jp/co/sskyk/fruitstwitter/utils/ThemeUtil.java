package jp.co.sskyk.fruitstwitter.utils;

import jp.co.sskyk.fruitstwitter.R;

/**
 * カラーUtility
 */
public class ThemeUtil {
    /**
     * カラーテーマ
     */
    public static class Theme {
        public static final int STRAWBERRY = 0;
        public static final int GRAPE = 1;
        public static final int GRAPEFRUIT = 2;
        public static final int MELON = 3;
        public static final int PINE = 4;
        public static final int WATERMELON = 5;
        public static final int PEACH = 6;
    }

    public static int getTheme(int theme) {
        switch (theme) {
            case Theme.STRAWBERRY:
                return R.style.themeStrawberry;
            case Theme.GRAPE:
                return R.style.themeGrape;
            case Theme.GRAPEFRUIT:
                return R.style.themeGrapefruit;
            case Theme.MELON:
                return R.style.themeMelon;
            case Theme.PINE:
                return R.style.themePine;
            case Theme.WATERMELON:
                return R.style.themeWaterMelon;
            case Theme.PEACH:
                return R.style.themePeach;
            default:
                return R.style.themeStrawberry;
        }
    }
}
