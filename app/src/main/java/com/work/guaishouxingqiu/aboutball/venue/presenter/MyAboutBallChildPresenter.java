package com.work.guaishouxingqiu.aboutball.venue.presenter;

import androidx.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultMyAboutBean;
import com.work.guaishouxingqiu.aboutball.venue.contract.MyAboutBallChildContract;
import com.work.guaishouxingqiu.aboutball.venue.model.MyAboutBallChildModel;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/22 18:02
 * 更新时间: 2019/5/22 18:02
 * 描述:
 */
public class MyAboutBallChildPresenter extends BasePresenter<MyAboutBallChildContract.View, MyAboutBallChildModel>
        implements MyAboutBallChildContract.Presenter {
    public MyAboutBallChildPresenter(@NonNull MyAboutBallChildContract.View view) {
        super(view);
    }

    @Override
    protected MyAboutBallChildModel createModel() {
        return new MyAboutBallChildModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(int flag) {
        mModel.loadData(flag, new BaseObserver<>(this, new BaseObserver.Observer<List<ResultMyAboutBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultMyAboutBean>> t) {
                if (DataUtils.isResultSure(t)) {
                    mView.resultData(t.result);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
