package com.steven.oschina.ui.synthetical.sub.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.oschina.client.base_library.banner.BannerAdapter;
import com.oschina.client.base_library.banner.BannerView;
import com.steven.oschina.ImageLoader;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.api.ServiceApi;
import com.steven.oschina.bean.banner.Banner;
import com.steven.oschina.bean.base.PageBean;
import com.steven.oschina.bean.base.ResultBean;
import com.steven.oschina.bean.sub.News;
import com.steven.oschina.ui.synthetical.article.WebActivity;
import com.steven.oschina.utils.UIHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * Data：11/23/2018-3:59 PM
 *
 * @author yanzhiwen
 */
public class BannerViewModel extends ViewModel {
    public final ServiceApi mServiceApi = RetrofitClient.getServiceApi();


    public final MutableLiveData<PageBean<Banner>> mObservableListLiveData = new MutableLiveData<>();

    public MutableLiveData<PageBean<Banner>> getBanner(int catalog) {
        mServiceApi.getBanner(catalog).enqueue(new Callback<ResultBean<PageBean<Banner>>>() {
            @Override
            public void onResponse(@NonNull Call<ResultBean<PageBean<Banner>>> call, @NonNull Response<ResultBean<PageBean<Banner>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isOk()) {
                    mObservableListLiveData.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultBean<PageBean<Banner>>> call, @NonNull Throwable t) {

            }
        });
        return mObservableListLiveData;

    }

    public void showBanner(Context context, BannerView bannerView, List<Banner> banners) {
        bannerView.setBannerTitle(banners.get(0).getName());
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView bannerIv;
                if (convertView != null) {
                    bannerIv = ( ImageView ) convertView;
                    bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    bannerIv = new ImageView(context);
                }
                ImageLoader.load(context, bannerIv, banners.get(position).getImg());
                return bannerIv;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return banners.get(position).getName();
            }
        });
        bannerView.startLoop();
        bannerView.setOnBannerItemClickListener(position -> {
            Banner banner = banners.get(position);
            if (banner != null) {
                int type = banner.getType();
                long id = banner.getId();
                if (type == News.TYPE_HREF) {
                    WebActivity.show(context, banner.getHref());
                } else {
                    UIHelper.showDetail(context, type, id, banner.getHref());
                }
            }
        });
    }
}
