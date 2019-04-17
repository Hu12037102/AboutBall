package com.work.guaishouxingqiu.aboutball.venue;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultAboutBallBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultTypeBean;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueData;
import com.work.guaishouxingqiu.aboutball.venue.bean.ResultVenueDetailsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/18 11:38
 * 更新时间: 2019/3/18 11:38
 * 描述:
 */
public interface VenueService {
    @GET(IApiService.VENUE_TYPE_LIST)
    Observable<BaseBean<List<ResultTypeBean>>> getBallTypeList();

    @GET(IApiService.VENUE_LIST_DATA)
    Observable<BaseBean<List<ResultVenueData>>> getVenueList(@Query(Contast.PAGE_NUM) int pageNum,
                                                             @Query(Contast.PAGE_SIZE) int pageSize,
                                                             @Query(Contast.TYPE_ID) int typeId,
                                                             @Query(Contast.LONGITUDE) String longitude,
                                                             @Query(Contast.LATITUDE) String latitude);

    @GET(IApiService.ABOUT_BALL_LIST_DATA)
    Observable<BaseBean<List<ResultAboutBallBean>>> getAboutBallList(@Query(Contast.PAGE_NUM) int pageNum,
                                                                     @Query(Contast.PAGE_SIZE) int pageSize);
    @GET(IApiService.GET_VENUE_DETAILS)
    Observable<BaseBean<ResultVenueDetailsBean>> getVenueDetails(@Query(Contast.STADIUM_ID) long stadiumId);
}
