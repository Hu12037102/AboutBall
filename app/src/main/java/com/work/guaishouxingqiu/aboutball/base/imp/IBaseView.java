package com.work.guaishouxingqiu.aboutball.base.imp;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;

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

}
