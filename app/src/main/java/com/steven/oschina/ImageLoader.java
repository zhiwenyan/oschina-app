package com.steven.oschina;

import android.content.Context;
import android.widget.ImageView;

/**
 * Description:
 * Data：7/5/2018-3:54 PM
 *
 * @author yanzhiwen
 */
public class ImageLoader {
    public static void load(Context context, ImageView imageView, String imagePath) {
        GlideApp.with(context)
                .load(imagePath)
                .fitCenter()
                .into(imageView);
    }
}
