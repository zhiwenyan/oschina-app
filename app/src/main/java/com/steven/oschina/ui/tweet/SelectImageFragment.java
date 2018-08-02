package com.steven.oschina.ui.tweet;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.greenfarm.client.recyclerview.adapter.OnItemClickListener;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.media.ImageFolderPopupWindow;
import com.steven.oschina.media.SpaceGridItemDecoration;
import com.steven.oschina.media.Util;
import com.steven.oschina.media.adapter.ImageAdapter;
import com.steven.oschina.media.adapter.ImageFolderAdapter;
import com.steven.oschina.media.bean.Image;
import com.steven.oschina.media.bean.ImageFolder;
import com.steven.oschina.ui.SelectOptions;
import com.steven.oschina.utils.TDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SelectImageFragment extends BaseFragment implements OnItemClickListener {
    @BindView(R.id.icon_back)
    ImageView mIconBack;
    @BindView(R.id.iv_title_select)
    ImageView mIvTitleSelect;
    @BindView(R.id.btn_title_select)
    Button mBtnTitleSelect;
    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    @BindView(R.id.btn_preview)
    Button mBtnPreview;
    @BindView(R.id.btn_done)
    Button mBtnDone;
    @BindView(R.id.lay_button)
    FrameLayout mLayButton;
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    private LoaderListener mLoaderListener = new LoaderListener();
    private List<Image> mImages;
    //被选中图片的集合
    private List<Image> mSelectedImage;
    private ImageAdapter mImageAdapter;
    private static SelectOptions mOption;
    private ImageFolderPopupWindow mImageFolderPopupWindow;
    private ImageFolderAdapter mImageFolderAdapter;
    private List<ImageFolder> mImageFolders;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_image;
    }

    public static SelectImageFragment newInstance(SelectOptions options) {
        mOption = options;
        return new SelectImageFragment();
    }

    @Override
    public void initData() {
        if (mOption == null) {
            return;
        }
        mSelectedImage = new ArrayList<>();
        mImages = new ArrayList<>();
        mImageFolders = new ArrayList<>();
        mRvImage.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRvImage.addItemDecoration(new SpaceGridItemDecoration(( int ) TDevice.dipToPx(getResources(), 1)));
        getLoaderManager().initLoader(0, null, mLoaderListener);
    }


    @OnClick({R.id.icon_back, R.id.iv_title_select, R.id.btn_title_select, R.id.btn_preview, R.id.btn_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                (( Activity ) mContext).finish();
                break;
            case R.id.iv_title_select:
                break;
            case R.id.btn_title_select:
                if (mImageFolderPopupWindow == null) {
                    mImageFolderPopupWindow = new ImageFolderPopupWindow(mContext, mImageFolders, new ImageFolderPopupWindow.Callback() {
                        @Override
                        public void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder imageFolder) {
                            addImagesToAdapter(imageFolder.getImages());
                            mRvImage.scrollToPosition(0);
                            mBtnTitleSelect.setText(imageFolder.getName());
                            popupWindow.dismiss();
                        }

                        @Override
                        public void onDismiss() {
                            mIvTitleSelect.setImageResource(R.mipmap.ic_arrow_bottom);
                        }

                        @Override
                        public void onShow() {
                            mIvTitleSelect.setImageResource(R.mipmap.ic_arrow_top);
                        }
                    });
                }
                addImageFolderToAdapter();
                mImageFolderPopupWindow.setAdapter(mImageFolderAdapter);
                mImageFolderPopupWindow.showAsDropDown(mToolbar);

                break;
            case R.id.btn_preview:
                if (mSelectedImage.size() > 0) {
                    ImageGalleryActivity.show(getActivity(), Util.toArray(mSelectedImage), 0, false);
                }
                break;
            case R.id.btn_done:
                if (mSelectedImage.size() > 0) {
                    handleResult();
                }
                break;
        }
    }


    private class LoaderListener implements LoaderManager.LoaderCallbacks<Cursor> {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            if (id == 0) {
                //数据库光标加载器
                return new CursorLoader(mContext,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                final ArrayList<Image> images = new ArrayList<>();
                //是否显示照相图片
                if (mOption.isHasCamera()) {
                    //添加到第一个的位置（默认）
                    images.add(new Image());
                }
                final ImageFolder defaultFolder = new ImageFolder();
                defaultFolder.setName("全部照片");
                defaultFolder.setPath("");
                mImageFolders.add(defaultFolder);

                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                        String thumbPath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                        String bucket = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));

                        Image image = new Image();
                        image.setPath(path);
                        image.setName(name);
                        image.setDate(dateTime);
                        image.setId(id);
                        image.setThumbPath(thumbPath);
                        image.setFolderName(bucket);

                        images.add(image);

                        //如果是新拍的照片
//                        if (mCamImageName != null && mCamImageName.equals(image.getName())) {
//                            image.setSelect(true);
//                            mSelectedImage.add(image);
//                        }

                        //如果是被选中的图片
                        if (mSelectedImage.size() > 0) {
                            for (Image i : mSelectedImage) {
                                if (i.getPath().equals(image.getPath())) {
                                    image.setSelect(true);
                                }
                            }
                        }

                        File imageFile = new File(path);
                        File folderFile = imageFile.getParentFile();
                        ImageFolder folder = new ImageFolder();
                        folder.setName(folderFile.getName());
                        folder.setPath(folderFile.getAbsolutePath());
                        if (!mImageFolders.contains(folder)) {
                            folder.getImages().add(image);
                            //默认相册封面
                            folder.setAlbumPath(image.getPath());
                            mImageFolders.add(folder);
                        } else {
                            // 更新
                            ImageFolder f = mImageFolders.get(mImageFolders.indexOf(folder));
                            f.getImages().add(image);
                        }
                    } while (data.moveToNext());
                }
                addImagesToAdapter(images);
                //全部照片
                defaultFolder.getImages().addAll(images);
                if (mOption.isHasCamera()) {
                    defaultFolder.setAlbumPath(images.size() > 1 ? images.get(1).getPath() : null);
                } else {
                    defaultFolder.setAlbumPath(images.size() > 0 ? images.get(0).getPath() : null);
                }
                //删除掉不存在的，在于用户选择了相片，又去相册删除
                if (mSelectedImage.size() > 0) {
                    List<Image> rs = new ArrayList<>();
                    for (Image i : mSelectedImage) {
                        File f = new File(i.getPath());
                        if (!f.exists()) {
                            rs.add(i);
                        }
                    }
                    mSelectedImage.removeAll(rs);
                }

//                // If add new mCamera picture, and we only need one picture, we result it.
//                if (mOption.getSelectCount() == 1 && mCamImageName != null) {
//                    handleResult();
//                }
//

//                handleSelectSizeChange(mSelectedImage.size());
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    }

    private void addImageFolderToAdapter() {
        if (mImageFolderAdapter == null) {
            mImageFolderAdapter = new ImageFolderAdapter(mContext, mImageFolders, R.layout.item_list_folder);
            mImageFolderPopupWindow.setAdapter(mImageFolderAdapter);
        } else {
            mImageFolderAdapter.notifyDataSetChanged();
        }
    }

    private void addImagesToAdapter(ArrayList<Image> images) {
        mImages.clear();
        mImages.addAll(images);
        if (mImageAdapter == null) {
            mImageAdapter = new ImageAdapter(mContext, mImages, (item, position) -> {
                if (TextUtils.isEmpty(item.getPath())) {
                    return R.layout.item_list_camera;
                }
                return R.layout.item_list_image;
            });
            mRvImage.setAdapter(mImageAdapter);
        } else {
            mImageAdapter.notifyDataSetChanged();
        }
        mImageAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        if (mOption.isHasCamera()) {
            if (position != 0) {
                handleSelectChange(position);
            } else {
                if (mSelectedImage.size() < mOption.getSelectCount()) {
                    //    mOperator.requestCamera();
                    // TODO: 8/2/2018
                } else {
                    showToast("最多只能选择 " + mOption.getSelectCount() + " 张图片");
                }
            }
        } else {
            handleSelectChange(position);
        }
    }


    private void handleSelectChange(int position) {
        Image image = mImages.get(position);
        if (image == null)
            return;
        //如果是多选模式
        final int selectCount = mOption.getSelectCount();
        if (selectCount > 1) {
            if (image.isSelect()) {
                image.setSelect(false);
                mSelectedImage.remove(image);
                if (mImageAdapter.getItemCount() > position) {
                    mImageAdapter.notifyItemChanged(position);
                }
            } else {
                if (mSelectedImage.size() == selectCount) {
                    showToast("最多只能选择 " + mOption.getSelectCount() + " 张图片");
                } else {
                    image.setSelect(true);
                    mSelectedImage.add(image);
                    if (mImageAdapter.getItemCount() > position) {
                        //通知具体位置的item发生变化
                        mImageAdapter.notifyItemChanged(position);
                    }
                }
            }
            handleSelectSizeChange(mSelectedImage.size());
        } else {
            mSelectedImage.add(image);
            handleResult();
        }
    }

    private void handleSelectSizeChange(int size) {
        if (size > 0) {
            mBtnPreview.setEnabled(true);
            mBtnDone.setEnabled(true);
            mBtnDone.setText(String.format("%s(%s)", getText(R.string.image_select_opt_done), size));
        } else {
            mBtnPreview.setEnabled(false);
            mBtnDone.setEnabled(false);
            mBtnDone.setText(getText(R.string.image_select_opt_done));
        }
    }

    private void handleResult() {
        if (mSelectedImage.size() != 0) {
            if (mOption.isCrop()) {
                List<String> selectedImage = mOption.getSelectedImages();
                selectedImage.clear();
                selectedImage.add(mSelectedImage.get(0).getPath());
                mSelectedImage.clear();
                // TODO: 8/2/2018
                //   CropActivity.show(this, mOption);
            } else {
                mOption.getCallback().doSelected(mSelectedImage);
                (( Activity ) mContext).finish();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        mOption = null;
        super.onDestroy();
    }
}
