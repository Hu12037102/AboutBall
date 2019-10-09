package com.ifeell.app.aboutball.my.contract;

import com.ifeell.app.aboutball.commonality.contract.LoginOrShareContract;
import com.ifeell.app.aboutball.my.bean.ResultTeamDetailsMemberBean;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/26 9:23
 * 更新时间: 2019/4/26 9:23
 * 描述:
 */
public interface BallTeamMemberContract {
    interface View extends LoginOrShareContract.View{
        void resultMemberDetails(List<ResultTeamDetailsMemberBean> data);

        void resultDeleteMember(int position);

    }
    interface Presenter extends LoginOrShareContract.Presenter{
        void loadMemberDetails(long teamId);

        void deleteMember(long teamId, long playId,int position);
    }
}
