package com.ifeell.app.aboutball.home;


import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.home.bean.RequestSendMessageBean;
import com.ifeell.app.aboutball.home.bean.ResultDoorTicketDetailsBean;
import com.ifeell.app.aboutball.home.bean.ResultGameTicketDetailsBean;
import com.ifeell.app.aboutball.home.bean.ResultHomeTabBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsMessageBean;
import com.ifeell.app.aboutball.home.bean.ResultNewsSearchBean;
import com.ifeell.app.aboutball.home.bean.ResultRecommendDataBean;
import com.ifeell.app.aboutball.home.bean.ResultTicketMallBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET(IApiService.GET_NEWS_MESSAGE_CONTENT_LIST)
    Observable<BaseBean<List<ResultNewsMessageBean>>> getNewsMessageData(@Query(Contast.PAGE_NUM) int pageNum,
                                                                         @Query(Contast.PAGE_SIZE) int pageSize,
                                                                         @Query(Contast.NEW_ID) long newsId);

    @POST(IApiService.POST_SEND_NEWS_MESSAGE_CONTENT)
    Observable<BaseBean<BaseDataBean<String>>> postSendMessageContent(@Body RequestSendMessageBean bean);

    @GET(IApiService.GET_SEARCH_NEWS_DATA)
    Observable<BaseBean<ResultNewsSearchBean>> getSearchNewsData(@Query(Contast.KEYWORD) String content,
                                                                 @Query(Contast.PAGE_NUM) int pageNum,
                                                                 @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.GET_TICKET_MALL_LIST)
    Observable<BaseBean<List<ResultTicketMallBean>>> getTicketMallList(@Query(Contast.SPU_CATEGORY_ID) int spuId);

    @GET(IApiService.GET_GAME_TICKET_DETAILS)
    Observable<BaseBean<ResultGameTicketDetailsBean>> getGameTicketDetails(@Query(Contast.SPU_ID) long skuId);
    @GET(IApiService.GET_DOOR_TICKET_DETAILS)
    Observable<BaseBean<ResultDoorTicketDetailsBean>> getDoorTicketDetails(@Query(Contast.SPU_ID) long skuId);
}
