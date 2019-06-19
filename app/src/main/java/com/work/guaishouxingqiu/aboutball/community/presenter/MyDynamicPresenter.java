package com.work.guaishouxingqiu.aboutball.community.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.contract.MyDynamicContract;
import com.work.guaishouxingqiu.aboutball.community.model.MyDynamicModel;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/19 9:09
 * 更新时间: 2019/6/19 9:09
 * 描述:我的动态P
 */
public class MyDynamicPresenter extends BasePresenter<MyDynamicContract.View, MyDynamicModel>
        implements MyDynamicContract.Presenter {
    public MyDynamicPresenter(@NonNull MyDynamicContract.View view) {
        super(view);
    }

    @Override
    protected MyDynamicModel createModel() {
        return new MyDynamicModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMyDynamic() {
        if (isRefresh) {
            mPageNum = Contast.DEFAULT_PAGE_NUM;
        }
        mModel.loadMyDynamic(mPageNum, mPageSize, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultCommunityDataBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultCommunityDataBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mPageNum++;
                    mView.resultMyDynamic(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
