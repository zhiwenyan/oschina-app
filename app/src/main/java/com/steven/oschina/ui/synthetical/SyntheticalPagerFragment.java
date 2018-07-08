package com.steven.oschina.ui.synthetical;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.steven.oschina.R;
import com.steven.oschina.base.BasePagerFragment;
import com.steven.oschina.bean.sub.SubTab;
import com.steven.oschina.ui.synthetical.sub.SubFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 综合
 */
public class SyntheticalPagerFragment extends BasePagerFragment {


    @BindView(R.id.iv_search)
    ImageView mIvSearch;

    public static SyntheticalPagerFragment newInstance() {
        return new SyntheticalPagerFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_synthetical_pager;
    }

    @Override
    public void initData() {
        super.initData();
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(EnglishArticleFragment.newInstance());
        fragments.add(ArticleFragment.newInstance());
        fragments.add(getSubFragment(6,
                "资讯",
                "https://www.oschina.net/action/apiv2/sub_list?token=e6142fa662bc4bf21083870a957fbd20",
                1,
                "e6142fa662bc4bf21083870a957fbd20"));
        fragments.add(getSubFragment(2,
                "问答",
                "https://www.oschina.net/action/apiv2/sub_list?token=98d04eb58a1d12b75d254deecbc83790",
                3,
                "98d04eb58a1d12b75d254deecbc83790"));
        fragments.add(getSubFragment(3,
                "博客",
                "https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9",
                4,
                "df985be3c5d5449f8dfb47e06e098ef9"));
        return fragments;
    }

    public String[] getPageTitles() {
        return getResources().getStringArray(R.array.synthesize_titles);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
    }

    private SubFragment getSubFragment(int type, String title, String url, int subType, String token) {
        SubTab tab = new SubTab();
        if (type == 3) {
            SubTab.Banner banner = tab.new Banner();
            banner.setCatalog(4);
            banner.setHref("https://www.oschina.net/action/apiv2/banner?catalog=4");
            tab.setBanner(banner);
        }
        tab.setType(type);
        tab.setFixed(false);
        tab.setName(title);
        tab.setNeedLogin(false);
        tab.setHref(url);
        tab.setSubtype(subType);
        tab.setToken(token);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", tab);
        return SubFragment.newInstance(tab);
    }

}
