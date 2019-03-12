package com.work.guaishouxingqiu.aboutball.home.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.home.contract.VideoContract;
import com.work.guaishouxingqiu.aboutball.home.model.VideoModel;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/12 18:08
 * 更新时间: 2019/3/12 18:08
 * 描述:视频P
 */
public class VideoPresenter  extends BasePresenter<VideoContract.View,VideoModel>
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
