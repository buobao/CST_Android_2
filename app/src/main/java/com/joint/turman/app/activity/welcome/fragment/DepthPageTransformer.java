package com.joint.turman.app.activity.welcome.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by dqf on 2016/1/7.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        if (position < -1) { // [-∞ ,-1)
            // 这一页已经是最左边的屏幕页
            page.setAlpha(0);
        } else if (position <= 0) { // [-1,0]
            // 向左面滑屏使用默认的过渡动画
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) { // (0,1]
            // 淡出页面
            page.setAlpha(1 - position);
            // 抵消默认的整页过渡
            page.setTranslationX(pageWidth * -position);
            // 根据缩放系数变化 (在 MIN_SCALE 和 1 之间变化)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else { // (1,+∞]
            // 这一页已经是最右边的屏幕页
            page.setAlpha(0);
        }
    }
}
