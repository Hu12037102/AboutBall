package com.work.guaishouxingqiu.aboutball.home;


import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultHomeTabBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultNewsBean;
import com.work.guaishouxingqiu.aboutball.home.bean.ResultRecommendDataBean;

import java.util.List;

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

    @GET(IApiService.NEWS_LIST)
    Observable<BaseBean<List<ResultNewsBean>>> newsData(@Query(Contast.PAGE_NUM) int pageNum,
                                                        @Query(Contast.PAGE_SIZE) int pageSize,
                                                        @Query(Contast.TYPE_ID) int typeId);

    @GET(IApiService.GET_HOME_TAB)
    Observable<BaseBean<List<ResultHomeTabBean>>> getHomeTab();

    @GET(IApiService.GET_NEWS_CONTENT)
    Observable<BaseBean<BaseDataBean<String>>> getNewsDetails(@Query(Contast.NEW_ID) long newsId);


}
