package com.joint.turman.app.activity.welcome.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joint.turman.app.activity.login.LoginActivity;
import com.joint.turman.cst_android_2.R;

/**
 * Created by dqf on 2016/1/7.
 */
public class ScreenSlideFragment extends Fragment implements View.OnClickListener {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screenslide,container,false);
        switch (getArguments().getInt("position")){
            case 0:
                rootView.findViewById(R.id.fragment_screenslide_image).setBackground(getResources().getDrawable(R.drawable.welcome01));
                break;
            case 1:
                rootView.findViewById(R.id.fragment_screenslide_image).setBackground(getResources().getDrawable(R.drawable.welcome02));
                break;
            case 2:
                rootView.findViewById(R.id.fragment_screenslide_image).setBackground(getResources().getDrawable(R.drawable.welcome03));
                View btn = rootView.findViewById(R.id.fragment_screenslide_btn);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(this);
                break;
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_screenslide_btn) {
            //Toast.makeText(getContext(),"click",Toast.LENGTH_SHORT).show();
            //登录
            getContext().startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }
}
