package com.ifeell.app.aboutball.community.presenter;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseObserver;
import com.ifeell.app.aboutball.base.BasePresenter;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;
import com.ifeell.app.aboutball.community.contract.SelectorTopicContract;
import com.ifeell.app.aboutball.community.model.SelectorTopicModel;
import com.ifeell.app.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 18:25
 * 更新时间: 2019/6/14 18:25
 * 描述:选择话题P
 */
public class SelectorTopicPresenter extends BasePresenter<SelectorTopicContract.View, SelectorTopicModel>
        implements SelectorTopicContract.Presenter {
    public SelectorTopicPresenter(@NonNull SelectorTopicContract.View view) {
        super(view);
    }

    @Override
    protected SelectorTopicModel createModel() {
        return new SelectorTopicModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTopicData() {
        mModel.loadTopicData(new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultTopicBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultTopicBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultTopicData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
