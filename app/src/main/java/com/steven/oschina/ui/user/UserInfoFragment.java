package com.steven.oschina.ui.user;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.widget.SolarSystemView;

import java.util.Random;

import butterknife.BindView;
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
        initSolar();
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
                mMaxRadius = (int) (mSolarSystemView.getHeight() - mPy + 250);
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
        for (int i = 40, radius = r + i; radius <= maxRadius; i = (int) (i * 1.4), radius += i) {
            SolarSystemView.Planet planet = new SolarSystemView.Planet();
            planet.setClockwise(random.nextInt(10) % 2 == 0);
            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
            planet.setRadius(radius);
            solarSystemView.addPlanets(planet);

        }
        solarSystemView.setPivotPoint(px, py);
    }
}
