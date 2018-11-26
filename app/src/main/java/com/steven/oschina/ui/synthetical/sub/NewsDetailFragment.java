package com.steven.oschina.ui.synthetical.sub;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Software;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.ui.synthetical.viewmodel.ArticleListViewModel;
import com.steven.oschina.widget.AutoScrollView;

/**
 * Description:
 * Data：7/26/2018-1:23 PM
 *
 * @author yanzhiwen
 */
public class NewsDetailFragment extends BaseSubDetailFragment<Article,ArticleListViewModel> {
    private TextView mTextSoftwareName;
    private LinearLayout mLinearSoftwareRoot;

    public static NewsDetailFragment newInstance(SubBean subBean) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        LinearLayout mLinearSoftware = mHeaderView.findViewById(R.id.ll_software);
        mLinearSoftwareRoot = mHeaderView.findViewById(R.id.ll_software_root);
        mTextSoftwareName = mHeaderView.findViewById(R.id.tv_software_name);
        mLinearSoftware.setOnClickListener(v -> {
            if (mSubBean != null && mSubBean.getSoftware() != null) {
                //  SoftwareDetailActivity.show(mContext, mSubBean.getSoftware().getId());

            }
        });
        Software software = mSubBean.getSoftware();
        if (software != null) {
            mLinearSoftwareRoot.setVisibility(View.VISIBLE);
            mTextSoftwareName.setText(software.getName());
        } else {
            mLinearSoftwareRoot.setVisibility(View.GONE);
        }
    }

    @Override
    protected View getHeaderView() {
        return new NewsDetailHeaderView(mContext);
    }

    private static class NewsDetailHeaderView extends AutoScrollView {
        public NewsDetailHeaderView(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.layout_news_detail_header, this, true);
        }
    }
}
