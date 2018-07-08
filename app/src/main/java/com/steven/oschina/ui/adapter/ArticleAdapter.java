package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.greenfarm.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.greenfarm.client.recyclerview.adapter.MultiTypeSupport;
import com.greenfarm.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.utils.DataFormat;

import java.util.List;

/**
 * Description:
 * Data：7/5/2018-4:19 PM
 *
 * @author yanzhiwen
 */

public class ArticleAdapter extends CommonRecyclerAdapter<Article> {

    public ArticleAdapter(Context context, List<Article> articles, MultiTypeSupport<Article> multiTypeSupport) {
        super(context, articles, multiTypeSupport);
    }

    @Override
    public void convert(ViewHolder holder, Article article) {
        holder.setText(R.id.tv_title, article.getTitle())
                .setViewVisibility(R.id.tv_origin, TextUtils.isEmpty(article.getSource()) ? View.GONE : View.VISIBLE)
                .setText(R.id.tv_origin, article.getSource())
                .setText(R.id.tv_author, TextUtils.isEmpty(article.getAuthorName()) ? "匿名" : article.getAuthorName())
                .setText(R.id.tv_time, DataFormat.parsePubDate(article.getPubDate()))
                .setText(R.id.tv_comment_count, article.getCommentCount() + "");
        String[] images = article.getImgs();
        if (images == null || images.length == 0) {
            holder.setText(R.id.tv_description, article.getDesc())
                    .setViewVisibility(R.id.tv_description, TextUtils.isEmpty(article.getDesc()) ? View.GONE : View.VISIBLE);
        } else if (images.length < 3) {
            holder.setText(R.id.tv_description, article.getDesc())
                    .setViewVisibility(R.id.tv_description, TextUtils.isEmpty(article.getDesc()) ? View.GONE : View.VISIBLE);
            holder.setImageByUrl(R.id.iv_image, new ViewHolder.HolderImageLoader(images[0]) {
                @Override
                public void displayImage(Context context, ImageView imageView, String imagePath) {
                    ImageLoader.load(context, imageView, imagePath);
                }
            });
        } else {
            holder.setImageByUrl(R.id.iv_image_1, new ViewHolder.HolderImageLoader(images[0]) {
                @Override
                public void displayImage(Context context, ImageView imageView, String imagePath) {
                    ImageLoader.load(context, imageView, imagePath);
                }
            });
            holder.setImageByUrl(R.id.iv_image_2, new ViewHolder.HolderImageLoader(images[1]) {
                @Override
                public void displayImage(Context context, ImageView imageView, String imagePath) {
                    ImageLoader.load(context, imageView, imagePath);
                }
            });
            holder.setImageByUrl(R.id.iv_image_3, new ViewHolder.HolderImageLoader(images[2]) {
                @Override
                public void displayImage(Context context, ImageView imageView, String imagePath) {
                    ImageLoader.load(context, imageView, imagePath);
                }
            });
        }
    }
}
