package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.base.imp.IBasePresenter;
import com.ifeell.app.aboutball.base.imp.IBaseView;
import com.ifeell.app.aboutball.my.bean.RequestAddRecordBean;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/4 17:21
 * 更新时间: 2019/6/4 17:21
 * 描述:添加球员记录契约
 */
public interface AddBallPeopleRecordContract {
    interface View extends IBaseView {
        void resultMemberDetails(List<ResultTeamDetailsMemberBean> data);

        void resultSaveRecord();

        void resultDeleteRecordSucceed();
    }

    interface Presenter extends IBasePresenter {
        void loadMemberDetails(long mHostTeamId);

        void addRefereePlayerRecord(RequestAddRecordBean mRequestBean);

        void refereeDeleteRecord(long outsId);

        void editRefereePlayerRecord(RequestAddRecordBean mRequestBean);
    }
}
