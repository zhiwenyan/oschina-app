package com.steven.oschina.ui.user;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.widget.SolarSystemView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserInfoFragment extends BaseFragment {
    @BindView(R.id.user_view_solar_system)
    SolarSystemView mSolarSystemView;
    @BindView(R.id.iv_portrait)
    CircleImageView mIvAvatar;
    @BindView(R.id.rl_show_my_info)
    LinearLayout mRlShowInfo;
    @BindView(R.id.user_info_icon_container)
    FrameLayout mFlUserInfoIconContainer;
    @BindView(R.id.iv_logo_setting)
    ImageView mIvLogoSetting;
    @BindView(R.id.iv_logo_zxing)
    ImageView mIvLogoZxing;
    @BindView(R.id.user_info_head_container)
    FrameLayout mUserInfoHeadContainer;
    @BindView(R.id.iv_gender)
    ImageView mIvGender;
    @BindView(R.id.tv_nick)
    TextView mTvNick;
    @BindView(R.id.tv_avail_score)
    TextView mTvAvailScore;
    @BindView(R.id.tv_active_score)
    TextView mTvActiveScore;
    @BindView(R.id.about_line)
    View mAboutLine;
    @BindView(R.id.tv_tweet)
    TextView mTvTweet;
    @BindView(R.id.ly_tweet)
    LinearLayout mLyTweet;
    @BindView(R.id.tv_favorite)
    TextView mTvFavorite;
    @BindView(R.id.ly_favorite)
    LinearLayout mLyFavorite;
    @BindView(R.id.tv_following)
    TextView mTvFollowing;
    @BindView(R.id.ly_following)
    LinearLayout mLyFollowing;
    @BindView(R.id.tv_follower)
    TextView mTvFollower;
    @BindView(R.id.user_info_notice_fans)
    TextView mUserInfoNoticeFans;
    @BindView(R.id.ly_follower)
    LinearLayout mLyFollower;
    @BindView(R.id.lay_about_info)
    LinearLayout mLayAboutInfo;
    @BindView(R.id.user_info_notice_message)
    TextView mUserInfoNoticeMessage;
    @BindView(R.id.rl_message)
    LinearLayout mRlMessage;
    @BindView(R.id.rl_read)
    LinearLayout mRlRead;
    @BindView(R.id.rl_blog)
    LinearLayout mRlBlog;
    @BindView(R.id.rl_info_question)
    LinearLayout mRlInfoQuestion;
    @BindView(R.id.rl_info_activities)
    LinearLayout mRlInfoActivities;
    @BindView(R.id.rl_info_tags)
    LinearLayout mRlInfoTags;
    @BindView(R.id.rl_team)
    LinearLayout mRlTeam;
    @BindView(R.id.rl_share)
    LinearLayout mRlShare;
    Unbinder unbinder;
    private int mMaxRadius;
    private int mR;
    private float mPx;
    private float mPy;

    public static UserInfoFragment newInstance() {

        Bundle args = new Bundle();

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initData() {
        //  initSolar();
    }


    /**
     * init solar view
     */
    private void initSolar() {
        View root = this.mRootView;
        if (root != null) {
            root.post(() -> {
                if (mRlShowInfo == null) return;
                int width = mRlShowInfo.getWidth();
                float rlShowInfoX = mRlShowInfo.getX();

                int height = mFlUserInfoIconContainer.getHeight();
                float y1 = mFlUserInfoIconContainer.getY();

                float x = mIvAvatar.getX();
                float y = mIvAvatar.getY();
                int portraitWidth = mIvAvatar.getWidth();

                mPx = x + +rlShowInfoX + (width >> 1);
                mPy = y1 + y + (height - y) / 2;
                mMaxRadius = ( int ) (mSolarSystemView.getHeight() - mPy + 250);
                mR = (portraitWidth >> 1);

                updateSolar(mPx, mPy);

            });
        }
    }

    /**
     * update solar
     *
     * @param px float
     * @param py float
     */
    private void updateSolar(float px, float py) {

        SolarSystemView solarSystemView = mSolarSystemView;
        Random random = new Random(System.currentTimeMillis());
        int maxRadius = mMaxRadius;
        int r = mR;
        solarSystemView.clear();
        for (int i = 40, radius = r + i; radius <= maxRadius; i = ( int ) (i * 1.4), radius += i) {
            SolarSystemView.Planet planet = new SolarSystemView.Planet();
            planet.setClockwise(random.nextInt(10) % 2 == 0);
            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
            planet.setRadius(radius);
            solarSystemView.addPlanets(planet);

        }
        solarSystemView.setPivotPoint(px, py);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_logo_setting, R.id.iv_logo_zxing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.iv_logo_zxing:
                break;
        }
    }
}
