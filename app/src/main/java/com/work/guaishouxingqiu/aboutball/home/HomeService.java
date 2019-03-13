package com.work.guaishouxingqiu.aboutball.home;


import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/13 13:32
 * 更新时间: 2019/3/13 13:32
 * 描述: 首页相关请求接口
 */
public interface HomeService {
    @GET(IApiService.RECOMMEND_HEAD)
    Observable<BaseBean<ResultRecommendDataBean>> headData(@Query(Contast.LONGITUDE) String longitude,
                                                           @Query(Contast.LATITUDE) String latitude);
}
