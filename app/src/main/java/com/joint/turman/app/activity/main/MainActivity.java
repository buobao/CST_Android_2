package com.joint.turman.app.activity.main;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.joint.turman.app.activity.beans.Fragments;
import com.joint.turman.app.base.BaseActivity;
import com.joint.turman.app.widget.guillotine.animation.GuillotineAnimation;
import com.joint.turman.app.widget.toolbarmenu.ContextMenuDialogFragment;
import com.joint.turman.app.widget.toolbarmenu.MenuObject;
import com.joint.turman.app.widget.toolbarmenu.MenuParams;
import com.joint.turman.app.widget.toolbarmenu.interfaces.OnMenuItemClickListener;
import com.joint.turman.app.widget.toolbarmenu.interfaces.OnMenuItemLongClickListener;
import com.joint.turman.cst_android_2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by dqf on 2016/1/7.
 */
public class MainActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private static final long RIPPLE_DURATION = 250;

    @InjectView(R.id.act_main_toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.act_main_root)
    protected FrameLayout root;
    @InjectView(R.id.act_main_hamburger)
    protected View contentHamburger;
    @InjectView(R.id.act_main_tabhost)
    protected FragmentTabHost fragmentTabHost;

    private ContextMenuDialogFragment menuDialogFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        init_menu();
        init_bottom_tab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    menuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化菜单
     */
    private void init_menu(){
        //设置工具栏
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //toolbar.setNavigationIcon(R.drawable.btn_back);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        //初始化toolbar menu
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        menuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        menuDialogFragment.setItemClickListener(this);
        menuDialogFragment.setItemLongClickListener(this);
    }

    /**
     * 添加菜单项
     * @return
     */
    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);
        close.setBgColor(R.color.colorGreen);

        MenuObject send = new MenuObject("反馈");
        send.setResource(R.drawable.icn_1);
        send.setBgColor(R.color.colorGreen);

        MenuObject addFr = new MenuObject("签到");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);
        addFr.setBgColor(R.color.colorGreen);

        MenuObject block = new MenuObject("请假");
        block.setResource(R.drawable.icn_5);
        block.setBgColor(R.color.colorGreen);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(addFr);
        menuObjects.add(block);
        return menuObjects;
    }

    /**
     * 初始化底部切换菜单
     */
    private void init_bottom_tab(){
        fragmentTabHost.setup(this, fragmentManager, R.id.act_main_content);

        for (int i=0;i<Fragments.values().length;i++) {
            Fragments fg = Fragments.getItem(i);
            View view = View.inflate(MainActivity.this, R.layout.activity_main_tabcontent, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            TextView textView=(TextView) view.findViewById(R.id.tab_titile);
            imageView.setImageResource(fg.getResIcon());
            textView.setText(fg.getResName());
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(getString(fg.getResName())).setIndicator(view);
            fragmentTabHost.addTab(spec, fg.getClz(), null);
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}

















