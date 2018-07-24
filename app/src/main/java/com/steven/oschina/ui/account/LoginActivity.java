package com.steven.oschina.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.simple.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.iv_login_logo)
    ImageView mIvLoginLogo;

    @BindView(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;

    @BindView(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;

    @BindView(R.id.iv_login_hold_pwd)
    ImageView mIvHoldPwd;
    @BindView(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;

    @BindView(R.id.bt_login_submit)
    Button mBtLoginSubmit;
    @BindView(R.id.bt_login_register)
    Button mBtLoginRegister;

    @BindView(R.id.ll_login_layer)
    View mLlLoginLayer;
    @BindView(R.id.ll_login_pull)
    LinearLayout mLlLoginPull;

    @BindView(R.id.ll_login_options)
    LinearLayout mLlLoginOptions;

    @BindView(R.id.ib_login_weibo)
    ImageView mIbLoginWeiBo;
    @BindView(R.id.ib_login_wx)
    ImageView mIbLoginWx;
    @BindView(R.id.ib_login_qq)
    ImageView mImLoginQq;
    @BindView(R.id.lay_login_container)
    LinearLayout mLayLoginContainer;
    @BindView(R.id.ib_login_csdn)
    ImageView mIbLoginCsdn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    public static void show(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    public static void show(Activity context, int requestCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivityForResult(intent, requestCode);
    }


    public static void show(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @OnClick({R.id.bt_login_submit, R.id.bt_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login_submit:
                String username = getEditTextToString(mEtLoginUsername);
                String password = getEditTextToString(mEtLoginPwd);
                if (TextUtils.isEmpty(username)) {
                    showToast("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                login(username, password);
                break;
            case R.id.bt_login_register:
                break;
        }
    }

    private void login(String username, String password) {
        HttpUtils._get(RetrofitClient.getServiceApi().login(username, getSha1(password)), new HttpCallback<User>() {
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                LoginSuccess(user);
            }
        });
    }

    private void LoginSuccess(User user) {

    }

    private String getEditTextToString(EditText edt) {
        return edt.getText().toString().trim();
    }

    /**
     * sha-1 to hex
     *
     * @param tempPwd tempPwd
     * @return sha-1 pwd
     */
    @NonNull
    protected String getSha1(String tempPwd) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(tempPwd.getBytes("utf-8"));
            byte[] bytes = messageDigest.digest();

            StringBuilder tempHex = new StringBuilder();
            // 字节数组转换为 十六进制数
            for (byte aByte : bytes) {
                String shaHex = Integer.toHexString(aByte & 0xff);
                if (shaHex.length() < 2) {
                    tempHex.append(0);
                }
                tempHex.append(shaHex);
            }
            return tempHex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return tempPwd;
    }
}
