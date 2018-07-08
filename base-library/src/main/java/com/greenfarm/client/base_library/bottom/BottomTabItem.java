package com.greenfarm.client.base_library.bottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by yanzhiwen on 2017/10/22.
 * BottomTabItem  基类
 */
public abstract class BottomTabItem {
    // 布局 id ，Context，最好采用Builder设计模式
    // 底部的子 item
    private View mTabItemView;
    private int mLayoutId;
    private Context mContext;

    public BottomTabItem(int layoutId,Context context){
        this.mLayoutId = layoutId;
        this.mContext = context;
    }

    /**
     * 获取底部条目的显示
     * @return
     */
    public View getTabView(){
        if(mTabItemView == null){
            mTabItemView = LayoutInflater.from(mContext).inflate(mLayoutId,null);
            initLayout();
        }
        return mTabItemView;
    }

    /**
     * 初始化显示
     */
    protected abstract void initLayout();

    protected <T> T findViewById(int id){
        return (T)mTabItemView.findViewById(id);
    }

    /**
     * 是否选择当前条目
     * @param selected
     */
    protected abstract void setSelected(boolean selected);
}
