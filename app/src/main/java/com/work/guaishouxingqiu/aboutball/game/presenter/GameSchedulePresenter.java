package com.work.guaishouxingqiu.aboutball.game.presenter;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseObserver;
import com.work.guaishouxingqiu.aboutball.base.BasePresenter;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameScheduleBean;
import com.work.guaishouxingqiu.aboutball.game.contract.GameScheduleContract;
import com.work.guaishouxingqiu.aboutball.game.model.GameScheduleModel;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.DateUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/11 9:32
 * 更新时间: 2019/6/11 9:32
 * 描述:赛程P
 */
public class GameSchedulePresenter extends BasePresenter<GameScheduleContract.View, GameScheduleModel>
        implements GameScheduleContract.Presenter {
    public GameSchedulePresenter(@NonNull GameScheduleContract.View view) {
        super(view);
    }

    @Override
    protected GameScheduleModel createModel() {
        return new GameScheduleModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadScheduleData(String requestTime) {
        mModel.loadScheduleData(requestTime, new BaseObserver<>(true, this, new BaseObserver.Observer<List<ResultGameScheduleBean>>() {
            @Override
            public void onNext(BaseBean<List<ResultGameScheduleBean>> t) {
                if (t.code == IApi.Code.SUCCEED) {
                    if (t.result != null) {
                        mView.resultScheduleData(t.result);
                    } else {
                        mView.resultNotData();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }
}
