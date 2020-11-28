package com.rkgroup.glidecustomloader;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

/**
 * Second step create ThumbnailBuilderFactory
 */

public class ThumbnailBuilderFactory implements ModelLoaderFactory<String, Bitmap> {
    /**
     * {@link Context} that pass to {@link ThumbnailBuilder} class
     */
    private final Context mContext;
    public ThumbnailBuilderFactory(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ModelLoader<String, Bitmap> build(@NonNull MultiModelLoaderFactory multiFactory) {
        return new ThumbnailBuilder(mContext);
    }

    @Override
    public void teardown() {
        // empty

    }
}
