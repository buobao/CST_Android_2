package com.joint.turman.app.activity.welcome;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.joint.turman.app.activity.welcome.fragment.DepthPageTransformer;
import com.joint.turman.app.activity.welcome.fragment.ScreenSlideFragment;
import com.joint.turman.cst_android_2.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends FragmentActivity {

    private SharedPreferences mGlobalSettings;

    private static final int NUM_PAGES = 3;

    @InjectView(R.id.activity_welcome_pager)
    ViewPager mPager;

    private PagerAdapter mPageraAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);

        mPageraAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageraAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());


    }


    //pager适配器
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Fragment getItem(int position) {
            ScreenSlideFragment fragment = new ScreenSlideFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
