package com.ifeell.app.aboutball.venue;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.venue.bean.RequestCreateBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestInvitationBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestLauncherBallBean;
import com.ifeell.app.aboutball.venue.bean.RequestVenueOrderBean;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallBean;
import com.ifeell.app.aboutball.venue.bean.ResultAboutBallDetailsBean;
import com.ifeell.app.aboutball.venue.bean.ResultMyAboutBean;
import com.ifeell.app.aboutball.venue.bean.ResultMyBallTeamBean;
import com.ifeell.app.aboutball.venue.bean.ResultOrderDetailsBean;
import com.ifeell.app.aboutball.venue.bean.ResultRefereeBean;
import com.ifeell.app.aboutball.venue.bean.ResultTypeBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueBookBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueData;
import com.ifeell.app.aboutball.venue.bean.ResultVenueDetailsBean;
import com.ifeell.app.aboutball.venue.bean.ResultVenueEvaluateBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET(IApiService.GET_VENUE_RECOMMEND_DATA)
    Observable<BaseBean<List<ResultVenueData>>> getVenueRecommendList(@Query(Contast.STADIUM_ID) long stadiumId,
                                                                      @Query(Contast.LONGITUDE) String longitude,
                                                                      @Query(Contast.LATITUDE) String latitude);

    @GET(IApiService.GET_VENUE_BOOK)
    Observable<BaseBean<List<ResultVenueBookBean>>> getVenueBookList(@Query(Contast.AREA_ID) long areaId,
                                                                     @Query(Contast.DATE) String date);

    @GET(IApiService.GET_VENUE_WAIT_BOOK)
    Observable<BaseBean<List<ResultVenueBookBean>>> getVenueWaitBookList(@Query(Contast.AREA_ID) long areaId,
                                                                         @Query(Contast.DATE) String date);

    @POST(IApiService.POST_CREATE_VENUE_ORDER_ID)
    Observable<BaseBean<BaseDataBean<Long>>> createOrderId(@Body RequestVenueOrderBean bean);

    @GET(IApiService.GET_ORDER_DETAILS)
    Observable<BaseBean<ResultOrderDetailsBean>> getOrderDetails(@Query(Contast.ORDER_ID) long orderId);

    @GET(IApiService.GET_BAND_PHONE_PHONE_NUMBER)
    Observable<BaseBean<BaseDataBean<String>>> bandOrderPhoneNumber(@Query(Contast.ORDER_ID) long orderId,
                                                                    @Query(Contast.PHONE) String phoneNumber);

    @GET(IApiService.GET_REFEREE_LIST)
    Observable<BaseBean<List<ResultRefereeBean>>> loadRefereeList();

    @GET(IApiService.GET_MY_BALL_TEAM)
    Observable<BaseBean<List<ResultMyBallTeamBean>>> loadMyBallTeam();

    @POST(IApiService.POST_LAUNCHER_BALL)
    Observable<BaseBean<BaseDataBean<Long>>> launcherBall(@Body RequestLauncherBallBean ballBean);

    @GET(IApiService.GET_ABOUT_BALL_DETAILS)
    Observable<BaseBean<ResultAboutBallDetailsBean>> loadAboutBallDetails(@Query(Contast.AGREE_ID) long offerId);

    @GET(IApiService.GET_PLAY_REFEREE)
    Observable<BaseBean<BaseDataBean<String>>> playReferee(@Query(Contast.AGREE_ID) long offerId);

    @GET(IApiService.GET_MY_ABOUT_BALL)
    Observable<BaseBean<List<ResultMyAboutBean>>> getMyAboutBean(@Query(Contast.FLAG) int flag);

    @POST(IApiService.POST_INVITATION_BALL)
    Observable<BaseBean<BaseDataBean<Long>>> invitationBall(@Body RequestInvitationBallBean bean);

    @GET(IApiService.GET_CANCEL_ABOUT_BALL)
    Observable<BaseBean<String>> cancelAboutBall(@Query(Contast.AGREE_ID) long agreeId);

    @GET(IApiService.GET_VENUE_EVALUATE)
    Observable<BaseBean<ResultVenueEvaluateBean>> loadVenueEvaluate(@Query(Contast.AREA_ID) long areaId, @Query(Contast.FLAG) int flag,
                                                                    @Query(Contast.PAGE_NUM) int pageNum, @Query(Contast.PAGE_SIZE) int pageSize);

    @POST(IApiService.POST_EDIT_ABOUT_BALL)
    Observable<BaseBean<BaseDataBean<String>>> editAboutBall(@Body RequestCreateBallBean ballBean);
}
