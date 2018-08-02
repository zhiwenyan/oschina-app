package com.steven.oschina.media.adapter;

import android.content.Context;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.media.bean.ImageFolder;

import java.util.List;

/**
 * Description:
 * Data：8/2/2018-3:30 PM
 *
 * @author yanzhiwen
 */
public class ImageFolderAdapter extends CommonRecyclerAdapter<ImageFolder> {
    private Context mContext;

    public ImageFolderAdapter(Context context, List<ImageFolder> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, ImageFolder item) {
        holder.setText(R.id.tv_folder_name, item.getName())
                .setText(R.id.tv_size, "(" + item.getImages().size() + ")");
        ImageLoader.load(mContext,holder.getView(R.id.iv_folder),item.getAlbumPath());
    }
}
