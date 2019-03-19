package com.work.guaishouxingqiu.aboutball.community;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.community.bean.ResultCommunityDataBean;

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
    @GET(IApiService.COMMUNITY_ATTENTION_DATA)
    Observable<BaseBean<List<ResultCommunityDataBean>>> loadAttentionData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                          @Query(Contast.PAGE_SIZE) int pageSize);
}
