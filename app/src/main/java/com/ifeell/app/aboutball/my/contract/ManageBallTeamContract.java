package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestManageBallTeamBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/24 11:42
 * 更新时间: 2019/4/24 11:42
 * 描述:管理球队契约(创建和编辑球队)
 */
public interface ManageBallTeamContract {
    interface View extends IBaseView {
        void resultBallTypeList(List<ResultTypeBean> data);
        void resultManageTeam(long id);
        void resultEditTeam();

    }

    interface Presenter extends IBasePresenter {
        void createTeam(RequestManageBallTeamBean mRequestBean);

        void editTeam(RequestManageBallTeamBean mRequestBean);
    }
}
