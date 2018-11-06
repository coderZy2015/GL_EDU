package com.gl.education.home.utlis;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zy on 2018/6/29.
 */

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
