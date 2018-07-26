package com.steven.oschina.ui.synthetical.sub;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.greenfarm.client.base_library.utils.FragmentManagerHelper;
import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.SubBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public abstract class DetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //  @BindView(R.id.shareView)
//    CommentShareView mShareView;
    @BindView(R.id.lay_container)
    FrameLayout mLayContainer;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    private DetailFragment mDetailFragment;
    public SubBean mSubBean;
    private String mIdent;
    @BindView(R.id.lay_error)
    ProgressBar mProgressBar;

    @Override

    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        setDarkToolBar();
        mSubBean = ( SubBean ) getIntent().getSerializableExtra("sub_bean");
        mIdent = getIntent().getStringExtra("ident");
        onRequestData();
    }


    public void onRequestData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", mSubBean.getType());
        params.put("id", mSubBean.getId());
        if (!TextUtils.isEmpty(mIdent)) {
            params.put("ident", mIdent);
        }
        HttpUtils._get(RetrofitClient.getServiceApi().getDetail(params), new HttpCallback<SubBean>() {
            @Override
            public void onSuccess(SubBean result) {
                super.onSuccess(result);
                mSubBean = result;
                mDetailFragment = getDetailFragment();
                addFragment(R.id.lay_container, mDetailFragment);
                mDetailFragment.setOnCompleteListener(() -> {
                    if (mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void addFragment(int layoutId, DetailFragment fragment) {
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), layoutId);
        fragmentManagerHelper.add(fragment);
    }

    public abstract DetailFragment getDetailFragment();
}
