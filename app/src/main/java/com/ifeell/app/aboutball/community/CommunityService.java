package com.ifeell.app.aboutball.community;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.community.bean.RequestPublishDynamicBean;
import com.ifeell.app.aboutball.community.bean.ResultCommunityDataBean;
import com.ifeell.app.aboutball.community.bean.ResultRecommendHotBean;
import com.ifeell.app.aboutball.community.bean.ResultTopicBean;
import com.ifeell.app.aboutball.community.bean.ResultUserDynamicBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsMessageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/19 15:05
 * 更新时间: 2019/3/19 15:05
 * 描述:
 */
public interface CommunityService {
    @GET(IApiService.COMMUNITY_NEW_DATA)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadCommunityNewData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                             @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.COMMUNITY_ATTENTION_DATA)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadCommunityAttentionData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                                   @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.COMMUNITY_HOT_TOPIC)
    Observable<BaseBean<List<ResultRecommendHotBean>>> loadHotTopic();

    @GET(IApiService.COMMUNITY_RECOMMEND_DATA)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadRecommendData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                          @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.COMMUNITY_COMMENT_DATA)
    Observable<BaseBean<List<ResultNewsMessageBean>>> loadCommunityCommentData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                               @Query(Contast.PAGE_SIZE) int pageSize,
                                                                               @Query(Contast.TWEET_ID) long tweetId);

    @GET(IApiService.GET_COMMUNITY_DYNAMIC_TOPIC)
    Observable<BaseBean<List<ResultTopicBean>>> loadTopicData();

    @POST(IApiService.POST_PUBLISH_DYNAMIC)
    Observable<BaseBean<BaseDataBean<String>>> publishCommunityDynamic(@Body RequestPublishDynamicBean bean);

    @GET(IApiService.GET_REPORT_COMMUNITY)
    Observable<BaseBean<BaseDataBean<String>>> getReportCommunity(@Query(Contast.TWEET_ID) long tweetId, @Query(Contast.TIPS_REASON) String tipsReason);

    @GET(IApiService.GET_USER_DYNAMIC)
    Observable<BaseBean<ResultUserDynamicBean>> getMyDynamicData(@Query(Contast.PAGE_NUM) int pageNum, @Query(Contast.PAGE_SIZE) int pageSize,
                                                                 @Query(Contast.USER_ID) long userId);

    @GET(IApiService.GET_RECOMMEND_TOPIC)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadRecommendedTopic(@Query(Contast.TOPIC_ID) long topicId, @Query(Contast.PAGE_NUM) int pageNum,
                                                                             @Query(Contast.PAGE_SIZE) int pageSize);
    @GET(IApiService.GET_NEW_TOPIC)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadNewTopic(@Query(Contast.TOPIC_ID) long topicId, @Query(Contast.PAGE_NUM) int pageNum,
                                                                             @Query(Contast.PAGE_SIZE) int pageSize);
}
