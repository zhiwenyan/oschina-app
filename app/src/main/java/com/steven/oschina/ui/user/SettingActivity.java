package com.steven.oschina.ui.user;

import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.AppContext;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.utils.FileUtil;
import com.steven.oschina.utils.MethodsCompat;
import com.steven.oschina.widget.ToggleButton;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.rl_clean_cache)
    LinearLayout mRlCleanCache;
    @BindView(R.id.tb_double_click_exit)
    ToggleButton mTbDoubleClickExit;
    @BindView(R.id.rl_double_click_exit)
    LinearLayout mRlDoubleClickExit;
    @BindView(R.id.tb_clip)
    ToggleButton mTbClip;
    @BindView(R.id.rl_clip)
    LinearLayout mRlClip;
    @BindView(R.id.rl_feedback)
    FrameLayout mRlFeedback;
    @BindView(R.id.rl_about)
    FrameLayout mRlAbout;
    @BindView(R.id.rl_check_version)
    FrameLayout mRlCheckVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        calculateCacheSize();
    }


    /**
     * 计算缓存的大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = this.getFilesDir();
        File cacheDir = this.getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        if (AppContext.isMethodsCompat(Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat.getExternalCacheDir(this);
            fileSize += FileUtil.getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }


    @OnClick({R.id.rl_clip, R.id.rl_feedback, R.id.rl_about, R.id.rl_check_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_clip:
                break;
            case R.id.rl_feedback:
                break;
            case R.id.rl_about:
                break;
            case R.id.rl_check_version:
                break;
        }
    }
}
