package com.ifeell.app.aboutball.base.imp;

import androidx.annotation.NonNull;

import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.bean.OSSToken;
import com.ifeell.app.aboutball.base.bean.ResultSureOrderDialogBean;
import com.ifeell.app.aboutball.game.bean.ResultGameDataResultBean;
import com.ifeell.app.aboutball.home.bean.ResultRedPointInfoBean;
import com.ifeell.app.aboutball.my.bean.ResultBallDetailsBean;
import com.ifeell.app.aboutball.my.bean.ResultDynamicNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultFansFocusBean;
import com.ifeell.app.aboutball.my.bean.ResultMyMessageBean;
import com.ifeell.app.aboutball.my.bean.ResultRefereeLevelBean;
import com.ifeell.app.aboutball.my.bean.ResultRefundCauseBean;
import com.ifeell.app.aboutball.my.bean.ResultSystemNotificationBean;
import com.ifeell.app.aboutball.my.bean.ResultUpdateApkBean;
import com.ifeell.app.aboutball.my.bean.ResultWeiChatSingBean;
import com.ifeell.app.aboutball.venue.bean.ResultNotBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/4 13:24
 * 更新时间: 2019/3/4 13:24
 * 描述: MVPBaseView接口
 */
public interface IBaseView {
    void showLoadingView();

    void dismissLoadingView();

    void showToast(@NonNull String text);

    void resultBaseData(@NonNull BaseBean baseBean);

    void resultOSSToken(OSSToken ossBean);

    void resultLevelData(List<ResultRefereeLevelBean> data);

    void resultApkInfo(ResultUpdateApkBean result);

    void resultRefereeStatus(Integer status);

    void resultWeiChatSing(ResultWeiChatSingBean bean);

    void resultRefundCauseData(List<ResultRefundCauseBean> data);

    void resultTeamDetails(ResultBallDetailsBean bean);

    void resultJoinTeamSucceed();

    void resultDynamicCommentsSucceed();

    void resultAttentionTweetStatus(int position);

    void resultDianZanStatus(int position);

    void resultDeleteDynamicSucceed(int position);

    void resultFansFocus(ResultFansFocusBean bean);

    void resultShareCommunityDynamic();

    void resultGameResultDetails(List<ResultGameDataResultBean> data);

    void resultSureUseOrder(long orderId);

    void resultRedPointData(List<ResultRedPointInfoBean> data);

    void resultMyMessageList(List<ResultMyMessageBean> data);

    void resultDynamicNotificationData(List<ResultDynamicNotificationBean> data);

    void resultSystemNotificationData(List<ResultSystemNotificationBean> data);

    void resultClearRedPoint(boolean isSucceed);

    void resultCreateBallOrderId(String orderId);

    void resultNotBookData(List<ResultNotBookBean> data);
    void resultSettingPasswordSucceed(String token);

    void resultCanUserVenueList(List<ResultVenueData> data);
    void resultSureOrderDialog(BaseBean<ResultSureOrderDialogBean> bean);
    void resultGoodStatus(int goodStatus);
}
