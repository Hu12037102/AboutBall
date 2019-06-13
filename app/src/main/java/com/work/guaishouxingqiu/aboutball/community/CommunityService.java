package com.work.guaishouxingqiu.aboutball.community;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultRecommendHotBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
}
