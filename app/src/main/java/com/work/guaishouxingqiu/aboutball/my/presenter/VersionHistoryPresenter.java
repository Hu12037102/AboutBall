package com.work.guaishouxingqiu.aboutball.my.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultVersionHistoryBean;
import com.work.guaishouxingqiu.aboutball.my.contract.VersionHistoryContract;
import com.work.guaishouxingqiu.aboutball.my.model.VersionHistoryModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/7/4 10:58
 * 更新时间: 2019/7/4 10:58
 * 描述:历史版本P
 */
public class VersionHistoryPresenter extends BasePresenter<VersionHistoryContract.View, VersionHistoryModel>
        implements VersionHistoryContract.Presenter {
    public VersionHistoryPresenter(@NonNull VersionHistoryContract.View view) {
        super(view);
    }

    @Override
    protected VersionHistoryModel createModel() {
        return new VersionHistoryModel();
    }

    @Override
    public void start() {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadVersionHistoryData(mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultVersionHistoryBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultVersionHistoryBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultVersionHistoryData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
