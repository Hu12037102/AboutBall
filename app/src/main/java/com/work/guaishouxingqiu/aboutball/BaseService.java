package com.work.guaishouxingqiu.aboutball;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.bean.ResultSureOrderDialogBean;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRedPointInfoBean;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestBandOtherAccountBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestSettingPasswordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultDynamicNotificationBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultFansFocusBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyMessageBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultSystemNotificationBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.RequestCreateBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultNotBookBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/6/14 16:06
 * 更新时间: 2019/6/14 16:06
 * 描述:
 */
public interface BaseService {
    @POST(IApiService.COMMUNITY_DYNAMIC_COMMENT)
    Observable<BaseBean<BaseDataBean<String>>> postDynamicComments(@Body RequestDynamicCommentsBean bean);

    @GET(IApiService.GET_ATTENTION_TWEET)
    Observable<BaseBean<BaseDataBean<String>>> getAttentionTweet(@Query(Contast.CONCERN_ID) long concernId);

    @GET(IApiService.GET_CANCEL_ATTENTION_TWEET)
    Observable<BaseBean<BaseDataBean<String>>> getCancelAttentionTweet(@Query(Contast.CONCERN_ID) long concernId);

    @GET(IApiService.GET_DYNAMIC_DIAN_ZAN)
    Observable<BaseBean<BaseDataBean<String>>> dynamicsDianZan(@Query(Contast.TWEET_ID) long tweetId);

    @GET(IApiService.GET_CANCEL_DYNAMIC_DIAN_ZAN)
    Observable<BaseBean<BaseDataBean<String>>> dynamicsCancelDianZan(@Query(Contast.TWEET_ID) long tweetId);

    @GET(IApiService.GET_DELETE_DYNAMIC)
    Observable<BaseBean<BaseDataBean<String>>> deleteDynamics(@Query(Contast.TWEET_ID) long tweetId);

    @POST(IApiService.GET_BAND_THREE_ACCOUNT)
    Observable<BaseBean<String>> bandOtherAccount(@Body RequestBandOtherAccountBean bean);

    @GET(IApiService.GET_FANS_AND_FOCUS)
    Observable<BaseBean<ResultFansFocusBean>> loadFanFocus();

    @GET(IApiService.GET_SHARE_COMMUNITY_DYNAMIC)
    Observable<BaseBean<BaseDataBean<String>>> shareCommunityDynamic(@Query(Contast.TWEET_ID) long tweetId);

    @GET(IApiService.GET_SURE_USER_ORDER)
    Observable<BaseBean<BaseDataBean<String>>> sureUserOrder(@Query(Contast.ORDER_ID) long orderId);

    @GET(IApiService.GET_RED_POINT)
    Observable<BaseBean<List<ResultRedPointInfoBean>>> getReadPoint();

    @GET(IApiService.GET_MY_MESSAGE_LIST)
    Observable<BaseBean<List<ResultMyMessageBean>>> getMyMessageList();

    @GET(IApiService.GET_DYNAMIC_NOTIFICATION_LIST)
    Observable<BaseBean<List<ResultDynamicNotificationBean>>> getDynamicNotificationList(@Query(Contast.NOTICE_TYPE) int noticeType,
                                                                                         @Query(Contast.PAGE_NUM) int pageNum,
                                                                                         @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.GET_SYSTEM_NOTIFICATION_LIST)
    Observable<BaseBean<List<ResultSystemNotificationBean>>> getSystemNotificationList(@Query(Contast.NOTICE_TYPE) int noticeType,
                                                                                       @Query(Contast.PAGE_NUM) int pageNum,
                                                                                       @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.GET_CLEAR_RED_POINT)
    Observable<BaseBean<String>> clearRedPoint(@Query(Contast.NOTICE_TYPE) int noticeType);

    @POST(IApiService.POST_LAUNCHER_BALL)
    Observable<BaseBean<BaseDataBean<String>>> createPostBall(@Body RequestCreateBallBean ballBean);

    @GET(IApiService.GET_NOT_BOOK)
    Observable<BaseBean<List<ResultNotBookBean>>> gainNotBooking();

    @POST(IApiService.GET_SETTING_PASSWORD)
    Observable<BaseBean<LoginResultBean>> settingPassword(@Body RequestSettingPasswordBean bean);

    @GET(IApiService.GET_CAN_USER_STADIUM_LIST)
    Observable<BaseBean<List<ResultVenueData>>> getCanUserVenueList(@Query(Contast.PAGE_NUM) int pageNum,
                                                                    @Query(Contast.PAGE_SIZE) int pageSize,
                                                                    @Query(Contast.TYPE_ID) int typeId,
                                                                    @Query(Contast.LONGITUDE) String longitude,
                                                                    @Query(Contast.LATITUDE) String latitude);

    @GET(IApiService.GET_NEWS_SELLING_POINTS)
    Observable<BaseBean<BaseDataBean<String>>> newsSellingPoints(@Query(Contast.NEW_ID) long newsId);

    @GET(IApiService.GET_SURE_ORDER_DIALOG)
    Observable<BaseBean<ResultSureOrderDialogBean>> getSureOrderDialog(@Query(Contast.SPU_ID) long spuId,
                                                                       @Query(Contast.PARAMS) String params,
                                                                       @Query(Contast.NUM) int num);

    @GET(IApiService.GET_CHECK_OUT_ORDER_STATUS)
    Observable<BaseBean<Integer>> getCheckOutOrderStatus(@Query(Contast.ORDER_ID) long orderId);

}
