package com.otto.sdk.ui.component.support;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class DownloadImageUtil {

    private Context context;
    private ImageView imageView;

    private RequestManager glide;
    private boolean isCircleCrop = false;
    private int placeholder = -1;
    private int errorImage = -1;
    private boolean memoryManagement = false;

    public DownloadImageUtil(Context context) {
        this.context = context;
    }

    public DownloadImageUtil target(ImageView imageView){
        this.imageView = imageView;

        return this;
    }

    public DownloadImageUtil customRequestOptions(RequestOptions requestOptions){
        this.glide = configureGlide(requestOptions);

        return this;
    }

    public DownloadImageUtil setCircleCrop(boolean circleCrop) {
        isCircleCrop = circleCrop;

        return this;
    }

    public DownloadImageUtil setPlaceholder(int placeholder) {
        this.placeholder = placeholder;

        return this;
    }

    public DownloadImageUtil setErrorImage(int errorImage) {
        this.errorImage = errorImage;

        return this;
    }

    public DownloadImageUtil setMemoryManagement(boolean isUse){
        this.memoryManagement = isUse;

        return this;
    }

    public void start(String url){
        if(glide == null) glide = configureGlide();

        glide
                .load(url)
                .into(imageView);
    }

    public void start(File file){
        if(glide == null) glide = configureGlide();

        glide
                .load(file)
                .into(imageView);
    }

    private RequestManager configureGlide(RequestOptions requestOptions){
        requestOptions = defaultTransform();

        return Glide
                .with(context)
                .applyDefaultRequestOptions(requestOptions);
    }

    private RequestManager configureGlide(){
        return Glide
                .with(context)
                .applyDefaultRequestOptions(defaultTransform());
    }

    private RequestOptions defaultTransform(){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.centerCrop();

        if(isCircleCrop)
            requestOptions = requestOptions.circleCrop();

        if(placeholder != -1)
            requestOptions = requestOptions.placeholder(placeholder);

        if(errorImage != -1)
            requestOptions = requestOptions.placeholder(errorImage);

        if(memoryManagement){
            requestOptions = requestOptions.skipMemoryCache(true);
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        return requestOptions;
    }
}
