package com.steven.oschina.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.steven.oschina.GlideApp;
import com.steven.oschina.GlideRequest;
import com.steven.oschina.GlideRequests;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.ui.AppOperator;
import com.steven.oschina.utils.BitmapUtil;
import com.steven.oschina.widget.ImagePreviewView;
import com.steven.oschina.widget.PreviewerViewPager;

import java.io.File;
import java.util.concurrent.Future;

import butterknife.BindView;

public class ImageGalleryActivity extends BaseActivity {

    public static final String KEY_IMAGE = "images";
    public static final String KEY_COOKIE = "cookie_need";
    public static final String KEY_POSITION = "position";
    public static final String KEY_NEED_SAVE = "save";
    private String[] mImageSources;
    private int mCurrentPosition;
    private boolean mNeedSaveLocal;
    private boolean mNeedCookie;
    private boolean[] mImageDownloadStatus;
    private GlideRequests mImageLoader;

    @BindView(R.id.vp_image)
    PreviewerViewPager mVpImage;
    @BindView(R.id.tv_index)
    TextView mIndexTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }
    public static void show(Context context, String images) {
        show(context, images, true);
    }
    public static void show(Context context, String images, boolean needSaveLocal) {
        if (images == null)
            return;
        show(context, new String[]{images}, 0, needSaveLocal);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal, boolean needCookie) {
        if (images == null || images.length == 0)
            return;
//        if (images.length == 1 && !images[0].endsWith(".gif") && !images[0].endsWith(".GIF") && !needCookie) {
//            LargeImageActivity.show(context, images[0]);
//            return;
//        }
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra(KEY_IMAGE, images);
        intent.putExtra(KEY_POSITION, position);
        intent.putExtra(KEY_NEED_SAVE, needSaveLocal);
        intent.putExtra(KEY_COOKIE, needCookie);
        context.startActivity(intent);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal) {
        show(context, images, position, needSaveLocal, false);
    }


    public static void show(Context context, String[] images, int position) {
        show(context, images, position, true);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_gallery;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        mImageSources = bundle.getStringArray(KEY_IMAGE);
        mCurrentPosition = bundle.getInt(KEY_POSITION, 0);
        mNeedSaveLocal = bundle.getBoolean(KEY_NEED_SAVE, true);
        mNeedCookie = bundle.getBoolean(KEY_COOKIE, false);
        if (mImageSources != null) {
            // 初始化下载状态
            mImageDownloadStatus = new boolean[mImageSources.length];

        }
    }

    @Override
    protected void initData() {
        int length = mImageSources.length;
        if (mCurrentPosition < 0 || mCurrentPosition >= length) {
            mCurrentPosition = 0;
        }
        mIndexTv.setText(String.format("%s/%s", (mCurrentPosition + 1), length));
        //set adapter
        mVpImage.setAdapter(new ImagePagerAdapter());
        mVpImage.setCurrentItem(mCurrentPosition);
        mVpImage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPosition = position;
                mIndexTv.setText(String.format("%s/%s", (position + 1),length));
                // 滑动时自动切换当前的下载状态
                changeSaveButtonStatus(mImageDownloadStatus[position]);
            }
        });
    }

    private class ImagePagerAdapter extends PagerAdapter implements ImagePreviewView.OnReachBorderListener {
        private View.OnClickListener mFinishClickListener;

        @Override
        public int getCount() {
            return mImageSources.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(ImageGalleryActivity.this)
                    .inflate(R.layout.lay_gallery_page_item_contener, container, false);
            ImagePreviewView preIv = view.findViewById(R.id.iv_preview);
            preIv.setOnReachBorderListener(this);
            ProgressBar loading = view.findViewById(R.id.loading);
            ImageView defaultView = view.findViewById(R.id.iv_default);
            loadImage(position, mImageSources[position], preIv, defaultView, loading);
            preIv.setOnClickListener(getListener());
            container.addView(view);
            return view;
        }

        private View.OnClickListener getListener() {
            if (mFinishClickListener == null) {
                mFinishClickListener = v -> finish();
            }
            return mFinishClickListener;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(( View ) object);
        }

        @Override
        public void onReachBorder(boolean isReached) {
//            if (isReached) {
//                mVpImage.getChildAt(mCurrentPosition).getParent().requestDisallowInterceptTouchEvent(false);
//            } else {
//                //这样，就不会拦截触摸操作，子View就可以获得触摸事件
//                mVpImage.getChildAt(mCurrentPosition).getParent().requestDisallowInterceptTouchEvent(true);
//            }
            mVpImage.isInterceptable(isReached);



        }

        private void loadImage(final int position, final String urlOrPath,
                               final ImageView previewView,
                               final ImageView defaultView,
                               final ProgressBar loading) {
            loadImageDoDownAndGetOverrideSize(urlOrPath, (overrideW, overrideH, isTrue) -> {
                GlideRequest builder = getImageLoader().load(urlOrPath)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e,
                                                        Object model, Target<Drawable> target,
                                                        boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                defaultView.setVisibility(View.VISIBLE);
                                updateDownloadStatus(position, false);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource,
                                                           Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                updateDownloadStatus(position, true);
                                return false;
                            }
                        }).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                // If download or get option error we not set override
                if (isTrue && overrideW > 0 && overrideH > 0) {
                    builder = builder.override(overrideW, overrideH).fitCenter();
                }
                builder.into(previewView);
            });
        }

        private void loadImageDoDownAndGetOverrideSize(final String urlOrPath, final DoOverrideSizeCallback callback) {
            // In this save max image size is source
            final Future<File> future = getImageLoader().load(urlOrPath)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            AppOperator.runOnThread(() -> {
                try {
                    File sourceFile = future.get();
                    BitmapFactory.Options options = BitmapUtil.createOptions();
                    // First decode with inJustDecodeBounds=true to checkShare dimensions
                    options.inJustDecodeBounds = true;
                    // First decode with inJustDecodeBounds=true to checkShare dimensions
                    BitmapFactory.decodeFile(sourceFile.getAbsolutePath(), options);

                    int width = options.outWidth;
                    int height = options.outHeight;
                    BitmapUtil.resetOptions(options);

                    if (width > 0 && height > 0) {
                        // Get Screen
                        final Point point = getDisplayDimens();

                        // This max size
                        final int maxLen = Math.min(Math.min(point.y, point.x) * 5, 1366 * 3);
                        // Init override size
                        final int overrideW, overrideH;

                        if ((width / ( float ) height) > (point.x / ( float ) point.y)) {
                            overrideW = Math.min(width, maxLen);
                            overrideH = Math.min(height, point.y);
                        } else {
                            overrideW = Math.min(width, point.x);
                            overrideH = Math.min(height, maxLen);
                        }

                        // Call back on main thread
                        runOnUiThread(() -> callback.onDone(overrideW, overrideH, true));
                    } else {
                        // Call back on main thread
                        runOnUiThread(() -> callback.onDone(0, 0, false));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    // Call back on main thread
                    runOnUiThread(() -> callback.onDone(0, 0, false));
                }
            });
        }


    }

    private void updateDownloadStatus(int pos, boolean isOk) {
        mImageDownloadStatus[pos] = isOk;
        if (mCurrentPosition == pos) {
            changeSaveButtonStatus(isOk);
        }
    }

    private void changeSaveButtonStatus(boolean isShow) {
        if (mNeedSaveLocal) {
            findViewById(R.id.iv_save).setVisibility(isShow ? View.VISIBLE : View.GONE);
        } else
            findViewById(R.id.iv_save).setVisibility(View.GONE);
    }


    private synchronized GlideRequests getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = GlideApp.with(this);
        return mImageLoader;
    }

    private Point mDisplayDimens;

    private synchronized Point getDisplayDimens() {
        if (mDisplayDimens != null) {
            return mDisplayDimens;
        }
        Point displayDimens;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayDimens = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        mDisplayDimens = displayDimens;
        return mDisplayDimens;
    }


    interface DoOverrideSizeCallback {
        void onDone(int overrideW, int overrideH, boolean isTrue);
    }
}

