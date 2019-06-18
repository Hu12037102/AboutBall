package com.work.guaishouxingqiu.aboutball;

import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.RequestDynamicCommentsBean;

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
}
