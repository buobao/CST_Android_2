package com.joint.turman.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.joint.turman.app.widget.dialog.DialogControl;
import com.joint.turman.app.widget.dialog.WaitDialog;
import com.joint.turman.cst_android_2.R;

import java.util.Date;

public class BaseFragment extends Fragment implements View.OnClickListener {

	protected static final int STATE_NONE = 0;
	protected static final int STATE_REFRESH = 1;
	protected static final int STATE_LOADMORE = 2;

	protected int mState = STATE_NONE;//页面状态

    //时间戳
    protected static String timestamp;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化时间戳
        timestamp = String.valueOf(new Date().getTime());
    }

	protected WaitDialog showWaitDialog() {
		return showWaitDialog(R.string.loading);
	}

    protected void hideWaitDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            ((DialogControl) activity).hideWaitDialog();
        }
    }

	protected WaitDialog showWaitDialog(int resid) {
		FragmentActivity activity = getActivity();
		if (activity instanceof DialogControl) {
			return ((DialogControl) activity).showWaitDialog(resid);
		}
		return null;
	}

	@Override
	public void onClick(View v) {
	}

		public boolean onBackPressed() {
			return false;
		}


		public int[] getViewLocation(View view){
			int[] startingLocation = new int[2];
			view.getLocationOnScreen(startingLocation);
			startingLocation[0] += view.getWidth() / 2;
			return startingLocation;
		}
}
