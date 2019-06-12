package com.work.guaishouxingqiu.aboutball.my;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestActionRecordsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestAddRecordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestApplyRefereeBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestEditAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestFeedbackBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestManageBallTeamBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestNewAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestOrderEvaluateBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestRefundBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestTeamMyDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePasswordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePhoneBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateWeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMatchRefereeResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyAddress;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyOrderBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeLevelBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefereeRecordDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundCauseBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultRefundDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultWeiChatSingBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/28
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public interface MyService {
    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateNameBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountWeight(@Body RequestUpdateWeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateSexBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountHeight(@Body RequestUpdateHeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountBirthday(@Body RequestUpdateBirthdayBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountHeadPhoto(@Body RequestUpdateHeadPhotoBean bean);

    @GET(IApiService.UPDATE_APK_INFO)
    Observable<BaseBean<BaseDataBean<ResultUpdateApkBean>>> updateApkInfo(@Query(Contast.PHONE_MODEL) String phoneModel,
                                                                          @Query(Contast.VERSION) String version);

    @GET(IApiService.GET_MY_PRIZE)
    Observable<BaseBean<ResultPrizeBean>> getMyPrize(@Query(Contast.STATS) int state);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postNewAddress(@Body RequestNewAddressBean bean);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postEditAddress(@Body RequestEditAddressBean bean);

    @GET(IApiService.GET_MY_ADDRESS)
    Observable<BaseBean<ResultMyAddress>> getMyAddress();

    @GET(IApiService.GET_HAS_ADDRESS)
    Observable<BaseBean<String>> hasAddress();

    @POST(IApiService.POST_BAND_PHONE_NUMBER)
    Observable<BaseBean<LoginResultBean>> bindPhoneNumber(@Body RequestUpdatePhoneBean bean);

    @POST(IApiService.POST_UPDATE_PASSWORD)
    Observable<BaseBean<BaseDataBean<LoginResultBean>>> updatePassword(@Body RequestUpdatePasswordBean bean);

    @GET(IApiService.GET_MY_BALL_TEAM)
    Observable<BaseBean<List<ResultMyBallBean>>> loadMyBallTeam();

    @GET(IApiService.GET_OSS_TOKEN)
    Observable<BaseBean<OSSToken>> loadOSSToken();

    @POST(IApiService.POST_MANAGE_TEAM)
    Observable<BaseBean<BaseDataBean<Long>>> manageBallTeam(@Body RequestManageBallTeamBean bean);

    @GET(IApiService.GET_BALL_TEAM_DETAILS)
    Observable<BaseBean<ResultBallDetailsBean>> loadBallTeamDetails(@Query(Contast.TEAM_ID) long teamId);

    @GET(IApiService.GET_BALL_TEAM_MEMBER_DETAILS)
    Observable<BaseBean<List<ResultTeamDetailsMemberBean>>> loadBallTeamMember(@Query(Contast.TEAM_ID) long teamId);

    @POST(IApiService.POST_EDIT_TEAM_INFO)
    Observable<BaseBean<BaseDataBean<String>>> editTeamInfo(@Body RequestManageBallTeamBean bean);

    @GET(IApiService.GET_EXIT_TEAM_BALL)
    Observable<BaseBean<BaseDataBean<String>>> exitBallTeam(@Query(Contast.TEAM_ID) Long teamId, @Query(Contast.PLAYER_ID) Long playerId);

    @GET(IApiService.GET_DELETE_MEMBER)
    Observable<BaseBean<BaseDataBean<String>>> deleteMeber(@Query(Contast.TEAM_ID) long teamId, @Query(Contast.PLAYER_ID) long playerId);

    @GET(IApiService.GET_DISSOLUTION_BALL_TEAM)
    Observable<BaseBean<BaseDataBean<String>>> dissolutionBallTeam(@Query(Contast.TEAM_ID) long teamId);

    @POST(IApiService.POST_EDIT_PLAYER_INFO)
    Observable<BaseBean<BaseDataBean<String>>> saveMyPlayerInfo(@Body RequestTeamMyDetailsBean bean);

    @GET(IApiService.GET_REFEREE_LEVEL_LIST)
    Observable<BaseBean<List<ResultRefereeLevelBean>>> loadRefereeLevelList();

    @POST(IApiService.POST_APPLY_REFEREE_CERTIFICATE)
    Observable<BaseBean<String>> commitRefereeCredential(@Body RequestApplyRefereeBean bean);

    @GET(IApiService.GET_JUDGE_REFEREE_STATUS)
    Observable<BaseBean<Integer>> judgeRefereeStatus();

    @GET(IApiService.GET_SURE_REFEREE_STATUS)
    Observable<BaseBean<BaseDataBean<String>>> sureRefereeStatus();

    //我的裁判记录
    @GET(IApiService.GET_MY_REFEREE_RECORD)
    Observable<BaseBean<List<ResultRefereeRecordBean>>> loadMyRefereeRecord();

    //通过Id查找裁判记录
    @GET(IApiService.GET_REFEREE_RECORD)
    Observable<BaseBean<List<ResultRefereeRecordBean>>> loadRefereeRecord(@Query(Contast.REFEREE_ID) long refereeId);

    @GET(IApiService.GET_REFEREE_DETAILS)
    Observable<BaseBean<ResultRefereeDetailsBean>> loadRefereeDetails(@Query(Contast.REFEREE_ID) long refereeId);


    @POST(IApiService.POST_FEEDBACK)
    Observable<BaseBean<BaseDataBean<String>>> feedback(@Body RequestFeedbackBean bean);

    @GET(IApiService.GET_MY_ORDER)
    Observable<BaseBean<List<ResultMyOrderBean>>> loadMyOrder(@Query(Contast.ORDER_STATE) int orderStatus,
                                                              @Query(Contast.PAGE_NUM) int pageNum,
                                                              @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.GET_CANCEL_ORDER)
    Observable<BaseBean<BaseDataBean<String>>> cancelOrder(@Query(Contast.ORDER_ID) long orderId);

    @POST(IApiService.POST_EVALUATE_ORDER)
    Observable<BaseBean<BaseDataBean<String>>> evaluateOrder(@Body RequestOrderEvaluateBean bean);

    @GET(IApiService.GET_PAY_WEI_CHAT_SING)
    Observable<BaseBean<BaseDataBean<ResultWeiChatSingBean>>> payWeiChatSing(@Query(Contast.ORDER_ID) long orderId, @Query(Contast.TRADE_TYPE) String tradeType);

    @GET(IApiService.GET_REFUND_CAUSE)
    Observable<BaseBean<List<ResultRefundCauseBean>>> getRefundCause();

    @POST(IApiService.POST_REFUND_ORDER)
    Observable<BaseBean<BaseDataBean<String>>> refundOrder(@Body RequestRefundBean bean);

    @GET(IApiService.GET_CHECK_REFUND)
    Observable<BaseBean<BaseDataBean<ResultRefundDetailsBean>>> getCheckRefund(@Query(Contast.ORDER_ID) long orderId);

    @GET(IApiService.GET_REFEREE_EVALUATION)
    Observable<BaseBean<List<ResultInputEvaluationBean>>> getRefereeEvaluation(@Query(Contast.REFEREE_ID) long refereeId);

    @GET(IApiService.GET_TEAM_EVALUATION)
    Observable<BaseBean<List<ResultInputEvaluationBean>>> getTeamEvaluation(@Query(Contast.TEAM_ID) long teamId);

    @POST(IApiService.POST_EVALUATE_OPPONENT)
    Observable<BaseBean<BaseDataBean<String>>> postEvaluateOpponent(@Body RequestInputEvaluationBean bean);

    @POST(IApiService.POST_EVALUATE_REFEREE)
    Observable<BaseBean<BaseDataBean<String>>> postEvaluateReferee(@Body RequestInputEvaluationBean bean);

    @GET(IApiService.GET_MY_REFEREE_EVALUATION)
    Observable<BaseBean<List<ResultInputEvaluationBean>>> loadMyRefereeEvaluation();

    @GET(IApiService.GET_JOIN_TEAM)
    Observable<BaseBean<BaseDataBean<String>>> joinTeam(@Query(Contast.TEAM_ID) long teamId);

    @GET(IApiService.GET_REFEREE_MATCH_RECORD)
    Observable<BaseBean<List<ResultMatchRefereeResultBean>>> getRefereeMatchRecordData(@Query(Contast.AGREE_ID) long agreeId);

    @POST(IApiService.POST_SAVE_REFEREE_RECORD)
    Observable<BaseBean<BaseDataBean<String>>> saveRefereePlayerRecord(@Body RequestAddRecordBean bean);

    @GET(IApiService.GET_REFEREE_RECORD_DETAILS)
    Observable<BaseBean<ResultRefereeRecordDetailsBean>> getRefereeRecordDetails(@Query(Contast.AGREE_ID) long agreeId);

    /**
     * 赛况记录
     *
     * @param bean
     * @return
     */
    @POST(IApiService.POST_NOTE_SITUATION)
    Observable<BaseBean<BaseDataBean<String>>> goActionRecord(@Body RequestActionRecordsBean bean);

    @POST(IApiService.POST_UPDATE_PHONE_NUMBER)
    Observable<BaseBean<String>> updatePhoneNumber(@Body RequestUpdatePhoneBean bean);
}
