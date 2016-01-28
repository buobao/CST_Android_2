package com.joint.turman.app.activity.main.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.joint.turman.app.widget.tablayout.SlidingTabLayout;
import com.joint.turman.app.widget.tablayout.fragment.SimpleCardFragment;
import com.joint.turman.app.widget.tablayout.listener.OnTabSelectListener;
import com.joint.turman.app.widget.tablayout.utils.ViewFindUtils;
import com.joint.turman.cst_android_2.R;

/**
 * Created by dqf on 2016/1/21.
 */
public class MessageFragment extends Fragment implements OnTabSelectListener {
    private FragmentActivity mContext;
    private final String[] mTitles = {"交流", "公告", "通知"};
    private ViewPager vp;
    private SlidingTabLayout tabLayout;
    private View view;
    private int index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_message, null);
        vp = ViewFindUtils.find(view, R.id.frg_mymessage_list);
        vp.setAdapter(new MyPagerAdapter(getChildFragmentManager()));

        vp.setCurrentItem(index);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                index = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout = ViewFindUtils.find(view, R.id.frg_mymessage_tabs);
        tabLayout.setViewPager(vp);
        return view;
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = SimpleCardFragment.getInstance(mTitles[position],null);
            return f;
        }
    }
}






















