package com.steven.oschina.media.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.media.bean.Image;

import java.util.List;

/**
 * Description:
 * Data：8/2/2018-2:39 PM
 *
 * @author yanzhiwen
 */
public class TweetSelectImageAdapter extends CommonRecyclerAdapter<Image> {
    private Context mContext;
    private List<Image> mImages;
    private int mCount;

    public TweetSelectImageAdapter(Context context, List<Image> data, int layoutId) {
        super(context, data, layoutId);
        this.mContext = context;
        this.mImages = data;
        mCount = this.mImages.size();
    }

    @Override
    public void convert(ViewHolder holder, Image item) {
        ImageView iv_image = holder.getView(R.id.iv_content);
        ImageLoader.load(mContext, iv_image, item.getPath());
//        ImageView iv_delete = holder.getView(R.id.iv_delete);
//        if(this.getItemCount()<=0){
//            this.notifyDataSetChanged();
//        }
//        iv_delete.setOnClickListener(v -> {
//            for (int i = 0; i < mCount; i++) {
//                if (mImages.get(i) == item) {
//                    mImages.remove(i);
//                    TweetSelectImageAdapter.this.notifyItemRemoved(i);
//                    mCount = mImages.size();
//                }
//            }
//        });
    }
}
