package com.steven.oschina.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.AppContext;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.Author;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.comment.CommentBar;
import com.steven.oschina.utils.PlatformUtil;
import com.steven.oschina.utils.StringUtils;
import com.steven.oschina.widget.TweetTextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TweetDetailActivity extends BaseActivity {
    public static final String BUNDLE_KEY_TWEET = "BUNDLE_KEY_TWEET";
    private Tweet tweet;
    @BindView(R.id.iv_portrait)
    CircleImageView mIvPortrait;
    @BindView(R.id.tv_nick)
    TextView mTvNick;
    @BindView(R.id.identityView)
    TextView mIdentityView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_client)
    TextView mTvClient;
    @BindView(R.id.tv_content)
    TweetTextView mTvContent;
    @BindView(R.id.tweet_img_record)
    ImageView mTweetImgRecord;
    @BindView(R.id.tweet_tv_record)
    TextView mTweetTvRecord;
    @BindView(R.id.tweet_bg_record)
    RelativeLayout mTweetBgRecord;
    @BindView(R.id.tv_ref_title)
    TextView mTvRefTitle;
    @BindView(R.id.tv_ref_content)
    TextView mTvRefContent;
    @BindView(R.id.layout_ref)
    LinearLayout mLayoutRef;
    @BindView(R.id.layout_container)
    LinearLayout mLayoutContainer;
    @BindView(R.id.fragment_container)
    FrameLayout mContainer;
    @BindView(R.id.fl_footer)
    FrameLayout mCommentView;
    private CommentBar mDelegation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tweet_detail;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        setDarkToolBar();

        tweet = ( Tweet ) getIntent().getSerializableExtra(BUNDLE_KEY_TWEET);
        if (tweet == null) {
            AppContext.showToastShort("对象没找到");
            return;
        }
        mDelegation = CommentBar.delegation(this, mCommentView);

        mDelegation.hideFav();
        mDelegation.hideCommentCount();
        mDelegation.showLike();
        mDelegation.showDispatch();
        mDelegation.setLikeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  onClickThumbUp();
            }
        });
        mDelegation.setDispatchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onClickTransmit();
            }
        });
        mDelegation.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //    handleKeyDel();
                }
                return false;
            }
        });

        mDelegation.hideCommentCount();
        mDelegation.hideFav();

        mDelegation.getBottomSheet().setMentionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (AccountHelper.isLogin()) {
//                    UserSelectFriendsActivity.show(TweetDetailActivity.this, mDelegation.getBottomSheet().getEditText());
//                } else
//                    LoginActivity.show(TweetDetailActivity.this);
            }
        });

        mDelegation.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapterV2(this));
        mDelegation.getBottomSheet().showEmoji();
        mDelegation.getBottomSheet().hideSyncAction();
        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mDelegation.getBottomSheet().getCommentText().replaceAll("[\\s\\n]+", " ");
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(TweetDetailActivity.this, "请输入文字", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!AccountHelper.isLogin()) {
//                    UIHelper.showLoginActivity(TweetDetailActivity.this);
//                    return;
//                }
//                if (replies.size() > 0)
//                    content = mDelegation.getBottomSheet().getEditText().getHint() + ": " + content;
//                OSChinaApi.pubTweetComment(tweet.getId(), content, 0, publishCommentHandler);
            }
        });


        showTweetDetail(tweet);
        requestTweetDetail();
        TweetDetailViewPagerFragment fragment = TweetDetailViewPagerFragment.newInstance(tweet);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    public static void show(Context context, Tweet tweet) {
        Intent intent = new Intent(context, TweetDetailActivity.class);
        intent.putExtra(BUNDLE_KEY_TWEET, tweet);
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Tweet tweet = new Tweet();
        tweet.setId(id);
        show(context, tweet);
    }

    private void requestTweetDetail() {
        HttpUtils._get(RetrofitClient.getServiceApi().getTweetDetail(tweet.getId()), new HttpCallback<Tweet>() {
            @Override
            public void onSuccess(Tweet result) {
                super.onSuccess(result);
                tweet = result;
                showTweetDetail(result);
            }
        });
    }

    private void showTweetDetail(Tweet tweet) {
        Author author = tweet.getAuthor();
        ImageLoader.load(this, mIvPortrait, author.getPortrait());
        mTvNick.setText(TextUtils.isEmpty(author.getName()) ? "匿名用户" : author.getName());
        if (!TextUtils.isEmpty(tweet.getPubDate()))
            mTvTime.setText(StringUtils.formatSomeAgo(tweet.getPubDate()));
        PlatformUtil.setPlatFromString(mTvClient, tweet.getAppClient());
        if (!TextUtils.isEmpty(tweet.getContent())) {
            String content = tweet.getContent().replaceAll("[\n\\s]+", " ");
            mTvContent.setText(content);
            mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
        }
        // TODO: 7/18/2018  
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                if (tweet == null || tweet.getId() <= 0 || TextUtils.isEmpty(tweet.getContent()))
                    break;
                // TODO: 7/18/2018  
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
