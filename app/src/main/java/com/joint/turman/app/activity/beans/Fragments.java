package com.joint.turman.app.activity.beans;


import com.joint.turman.app.activity.main.fragment.KnowledgeFragment;
import com.joint.turman.app.activity.main.fragment.MessageFragment;
import com.joint.turman.app.activity.main.fragment.ProjectFragment;
import com.joint.turman.cst_android_2.R;

/**
 * Created by dqf on 2016/1/11.
 */
public enum Fragments {

    MESSAGE(0, R.string.bottom_message, R.drawable.tab_icon_workspace, MessageFragment.class),
    PROJECT(1, R.string.bottom_project, R.drawable.tab_icon_working, ProjectFragment.class),
    KNOWLEDGE(2, R.string.bottom_knowledge, R.drawable.tab_icon_community, KnowledgeFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private Fragments(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public static Fragments getItem(int i){
        for (Fragments f:Fragments.values()) {
            if (f.getIdx() == i) {
                return f;
            }
        }

        return null;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
