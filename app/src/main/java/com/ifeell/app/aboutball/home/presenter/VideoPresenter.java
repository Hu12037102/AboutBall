package com.ifeell.app.aboutball.home.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.home.contract.VideoContract;
import com.ifeell.app.aboutball.home.model.VideoModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:08
 * 更新时间: 2019/3/12 18:08
 * 描述:视频P
 */
public class VideoPresenter  extends HomeBasePresenter<VideoContract.View,VideoModel>
implements VideoContract.Presenter{
    public VideoPresenter(@NonNull VideoContract.View view) {
        super(view);
    }

    @Override
    protected VideoModel createModel() {
        return new VideoModel();
    }

    @Override
    public void start() {

    }
}
