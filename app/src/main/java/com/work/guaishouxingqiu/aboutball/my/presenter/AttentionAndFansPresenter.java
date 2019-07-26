package com.work.guaishouxingqiu.aboutball.my.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultAttentionFanBean;
import com.work.guaishouxingqiu.aboutball.my.contract.AttentionAndFansContract;
import com.work.guaishouxingqiu.aboutball.my.model.AttentionAndFansModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/26 11:05
 * 更新时间: 2019/7/26 11:05
 * 描述:关注和我的粉丝P
 */
public class AttentionAndFansPresenter extends BasePresenter<AttentionAndFansContract.View, AttentionAndFansModel>
        implements AttentionAndFansContract.Presenter {
    public AttentionAndFansPresenter(@NonNull AttentionAndFansContract.View view) {
        super(view);
    }

    @Override
    protected AttentionAndFansModel createModel() {
        return new AttentionAndFansModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadAttentionData() {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyAttentionData(mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultAttentionFanBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultAttentionFanBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultAttentionFansData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    @Override
    public void loadFansData() {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyFansData(mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultAttentionFanBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultAttentionFanBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultAttentionFansData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
