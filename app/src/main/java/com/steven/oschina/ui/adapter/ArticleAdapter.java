package com.steven.oschina.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.oschina.client.recyclerview.adapter.CommonRecyclerAdapter;
import com.oschina.client.recyclerview.adapter.MultiTypeSupport;
import com.oschina.client.recyclerview.adapter.ViewHolder;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.ui.synthetical.article.ArticleDetailActivity;
import com.steven.oschina.ui.synthetical.article.EnglishArticleDetailActivity;
import com.steven.oschina.ui.synthetical.article.WebActivity;
import com.steven.oschina.ui.synthetical.sub.BlogDetailActivity;
import com.steven.oschina.ui.synthetical.sub.NewsDetailActivity;
import com.steven.oschina.ui.synthetical.sub.QuestionDetailActivity;
import com.steven.oschina.utils.DataFormat;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.utils.TypeFormat;
import com.steven.oschina.utils.UIHelper;

import java.util.List;

/**
 * Description:
 * Data：7/5/2018-4:19 PM
 *
 * @author yanzhiwen
 */

public class ArticleAdapter extends CommonRecyclerAdapter<Article> {
    private Context mContext;

    public ArticleAdapter(Context context, List<Article> articles, MultiTypeSupport<Article> multiTypeSupport) {
        super(context, articles, multiTypeSupport);
        this.mContext = context;
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

        //setOnClickListener
        holder.itemView.setOnClickListener(v -> {
            if (!TDevice.hasWebView(mContext))
                return;
            if (article.getType() == 0) {
                if (TypeFormat.isGit(article)) {
                    WebActivity.show(mContext, TypeFormat.formatUrl(article));
                } else {
                    ArticleDetailActivity.show(mContext, article);
                }
            } else {
                int type = article.getType();
                long id = article.getOscId();
                switch (type) {
                    case News.TYPE_SOFTWARE:
                        //   SoftwareDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_QUESTION:
                        QuestionDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_BLOG:
                        BlogDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_TRANSLATE:
                        NewsDetailActivity.show(mContext, id, News.TYPE_TRANSLATE);
                        break;
                    case News.TYPE_EVENT:
                        //     EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        NewsDetailActivity.show(mContext, id);
                        break;
                    case Article.TYPE_ENGLISH:
                        EnglishArticleDetailActivity.show(mContext, article);
                        break;
                    default:
                        UIHelper.showUrlRedirect(mContext, article.getUrl());
                        break;
                }
            }
        });
    }
}
