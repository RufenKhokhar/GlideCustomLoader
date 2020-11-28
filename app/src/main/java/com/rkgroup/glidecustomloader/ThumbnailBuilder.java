package com.rkgroup.glidecustomloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 1st step
 * class contains the main logic for thumbnail generate
 * implements {@link ModelLoader} interface
 * {@link String} is the input and {@link Bitmap} is the output of the class
 */
public class ThumbnailBuilder implements ModelLoader<String, Bitmap> {
    private final Context mContext;

    public ThumbnailBuilder(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public LoadData<Bitmap> buildLoadData(@NonNull String input, int width, int height, @NonNull Options options) {
        return new LoadData<>(new ObjectKey(input), new ThumbnailCreator(input));
    }

    @Override
    public boolean handles(@NonNull String input) {
        // handles only pdf file
        return input.endsWith(".pdf");
    }


    private class ThumbnailCreator implements DataFetcher<Bitmap> {
        private final String input;

        public ThumbnailCreator(String input) {
            this.input = input;
        }

        @Override
        public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super Bitmap> callback) {
            try {
                Bitmap output;
                File photoCacheDir = Glide.getPhotoCacheDir(mContext.getApplicationContext());
                File thumbnail = new File(photoCacheDir, Uri.parse(input).getLastPathSegment() + ".png");
                // check if file is already exist then there is no need to re create it
                if (!thumbnail.exists()) {
                    PDDocument pdDocument = PDDocument.load(new File(input));
                    // create thumbnail for first page of pdf file
                    output = new PDFRenderer(pdDocument).renderImage(0);
                    output.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(thumbnail));

                    pdDocument.close();
                } else {
                    output = BitmapFactory.decodeFile(thumbnail.getAbsolutePath());
                }

                // send output data
                callback.onDataReady(output);


            } catch (Exception e) {
                // if error
                callback.onLoadFailed(e);
            }

        }

        @Override
        public void cleanup() {
            // empty

        }

        @Override
        public void cancel() {
            // empty

        }

        @NonNull
        @Override
        public Class<Bitmap> getDataClass() {
            // output data class
            return Bitmap.class;
        }

        @NonNull
        @Override
        public DataSource getDataSource() {
            // data source local or network base
            return DataSource.LOCAL;
        }

    }
}
