package com.joint.turman.app.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joint.turman.app.AppContext;
import com.joint.turman.app.utils.RxUtils;
import com.joint.turman.app.utils.TDevice;
import com.joint.turman.app.widget.dialog.CommonToast;
import com.joint.turman.app.widget.dialog.DialogControl;
import com.joint.turman.app.widget.dialog.DialogHelper;
import com.joint.turman.app.widget.dialog.WaitDialog;
import com.joint.turman.cst_android_2.R;

import java.util.Date;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dqf on 2016/1/11.
 */
public abstract class BaseActivity extends AppCompatActivity implements DialogControl, VisibilityControl, View.OnClickListener {

    protected static String timestamp;

    private CompositeSubscription _subscriptions;

    public CompositeSubscription getSubscriptions(){
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
        return _subscriptions;
    }

    public void addSubscription(Subscription subscription){
        getSubscriptions().add(subscription);
    }

    //是否可见
    private boolean _isVisible;

    //等待加载框
    private WaitDialog _waitDialog;

    protected LayoutInflater mInflater;    //当前 layout 对象
    protected Toolbar mActionBar;   //当前ActionBar

    private BroadcastReceiver mExistReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constants.INTENT_ACTION_EXIT.equals(intent.getAction())){
                finish();
            }else if(Constants.INTENT_ACTION_LOGOUT.equals(intent.getAction())){
                if(logoutAndFinish())
                    finish();
            }
        }
    };

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    //登出时注销activity
    private boolean logoutAndFinish() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.saveDisplaySize(this);

        //初始化时间戳
        timestamp = String.valueOf(new Date().getTime());

        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        init(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.INTENT_ACTION_EXIT);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
        registerReceiver(mExistReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mExistReceiver);
        mExistReceiver = null;
        RxUtils.unsubscribeIfNotNull(_subscriptions);
        super.onDestroy();
    }

    /**
     * setContentView Before事件
     */
    protected void onBeforeSetContentLayout() {
    }

    /**
     * Layout XML
     *
     * @return int
     */
    protected int getLayoutId() {
        return 0;
    }

    /**
     * 初始化View
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {
        ButterKnife.inject(this);
    }


    /**
     * 创建View实例
     * @param resId
     *
     * @return View
     */
    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        _isVisible = false;
        hideWaitDialog();
        super.onPause();
    }

    @Override
    protected void onResume() {
        _isVisible = true;
        super.onResume();
    }

    public void hideSoftKeyboard() {
        TDevice.hideSoftKeyboard(getCurrentFocus());
    }

    public void showToast(int msgResid) {
        showToast(getString(msgResid));
    }

    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message) {
        showToast(message, 0, 17);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public WaitDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public WaitDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public WaitDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getCancelableWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean isVisible() {
        return _isVisible;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public int[] getViewLocation(View view){
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        startingLocation[0] += view.getWidth() / 2;
        return startingLocation;
    }

    protected class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
        }
    }

}























