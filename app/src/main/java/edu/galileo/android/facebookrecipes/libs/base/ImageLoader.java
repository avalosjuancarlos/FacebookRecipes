package edu.galileo.android.facebookrecipes.libs.base;

import android.widget.ImageView;

/**
 * Created by avalo.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);
}
