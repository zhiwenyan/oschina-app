package com.steven.oschina.ui.synthetical.detail;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.steven.oschina.R;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.widget.AutoScrollView;


public class BlogDetailFragment extends DetailFragment {

    public static BlogDetailFragment newInstance(SubBean subBean) {
        BlogDetailFragment fragment = new BlogDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
    }

    @Override
    protected View getHeaderView() {
        return new BlogDetailHeaderView(mContext);
    }

    private static class BlogDetailHeaderView extends AutoScrollView {
        public BlogDetailHeaderView(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.layout_blod_detail_header, this, true);
        }
    }
}
