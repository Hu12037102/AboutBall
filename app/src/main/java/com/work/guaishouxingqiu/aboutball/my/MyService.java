package com.work.guaishouxingqiu.aboutball.my;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.base.bean.OSSToken;
import com.work.guaishouxingqiu.aboutball.http.IApi;
import com.work.guaishouxingqiu.aboutball.login.bean.LoginResultBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestEditAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestManageBallTeamBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestNewAddressBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateBirthdayBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeadPhotoBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateHeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateNameBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePasswordBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdatePhoneBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateSexBean;
import com.work.guaishouxingqiu.aboutball.my.bean.RequestUpdateWeightBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultBallDetailsBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyAddress;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultMyBallBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultPrizeBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultTeamDetailsMemberBean;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultUpdateApkBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项  目 :  AboutBall
 * 包  名 :  com.work.guaishouxingqiu.aboutball.my
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/3/28
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public interface MyService {
    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateNameBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountWeight(@Body RequestUpdateWeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountSex(@Body RequestUpdateSexBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountHeight(@Body RequestUpdateHeightBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountBirthday(@Body RequestUpdateBirthdayBean bean);

    @POST(IApiService.USER_ACCOUNT)
    Observable<BaseBean<BaseDataBean>> updateAccountHeadPhoto(@Body RequestUpdateHeadPhotoBean bean);

    @GET(IApiService.UPDATE_APK_INFO)
    Observable<BaseBean<BaseDataBean<ResultUpdateApkBean>>> updateApkInfo(@Query(Contast.PHONE_MODEL) String phoneModel,
                                                                          @Query(Contast.VERSION) String version);

    @GET(IApiService.GET_MY_PRIZE)
    Observable<BaseBean<ResultPrizeBean>> getMyPrize(@Query(Contast.STATS) int state);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postNewAddress(@Body RequestNewAddressBean bean);

    @POST(IApiService.POST_EDIT_ADDRESS)
    Observable<BaseBean<BaseDataBean>> postEditAddress(@Body RequestEditAddressBean bean);

    @GET(IApiService.GET_MY_ADDRESS)
    Observable<BaseBean<ResultMyAddress>> getMyAddress();

    @GET(IApiService.GET_HAS_ADDRESS)
    Observable<BaseBean<String>> hasAddress();

    @POST(IApiService.POST_UPDATE_PHONE_NUMBER)
    Observable<BaseBean<String>> updatePhoneNumber(@Body RequestUpdatePhoneBean bean);

    @POST(IApiService.POST_UPDATE_PASSWORD)
    Observable<BaseBean<BaseDataBean<LoginResultBean>>> updatePassword(@Body RequestUpdatePasswordBean bean);

    @GET(IApiService.GET_MY_BALL_TEAM)
    Observable<BaseBean<List<ResultMyBallBean>>> loadMyBallTeam();

    @GET(IApiService.GET_OSS_TOKEN)
    Observable<BaseBean<OSSToken>> loadOSSToken();

    @POST(IApiService.POST_MANAGE_TEAM)
    Observable<BaseBean<BaseDataBean<Long>>> manageBallTeam(@Body RequestManageBallTeamBean bean);

    @GET(IApiService.GET_BALL_TEAM_DETAILS)
    Observable<BaseBean<ResultBallDetailsBean>> loadBallTeamDetails(@Query(Contast.TEAM_ID) long teamId);

    @GET(IApiService.GET_BALL_TEAM_MEMBER_DETAILS)
    Observable<BaseBean<List<ResultTeamDetailsMemberBean>>> loadBallTeamMember(@Query(Contast.TEAM_ID) long teamId);

    @POST(IApiService.POST_EDIT_TEAM_INFO)
    Observable<BaseBean<BaseDataBean<String>>> editTeamInfo(@Body RequestManageBallTeamBean bean);

    @GET(IApiService.GET_EXIT_TEAM_BALL)
    Observable<BaseBean<BaseDataBean<String>>> exitBallTeam(@Query(Contast.TEAM_ID) Long teamId, @Query(Contast.PLAYER_ID) Long playerId);
}
