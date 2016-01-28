package com.joint.turman.app.activity.welcome.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by dqf on 2016/1/7.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            page.setAlpha(0);
        } else if (position <=1) {
            //修改默认的滑动过度效果为缩放效果
            float scaleFactor = Math.max(MIN_SCALE, 1- Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // 开始根据缩放系数进行变化 (在 MIN_SCALE 和 1 之间变化)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            // 根据大小（缩放系数）变化化透明度实现淡化页面效果
            page.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // (1,+∞ ]
            // 这一页已经是最右边的屏幕页
            page.setAlpha(0);
        }
    }
}
