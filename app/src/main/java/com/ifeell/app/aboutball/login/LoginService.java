package com.ifeell.app.aboutball.login;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.login.bean.LoginResultBean;
import com.ifeell.app.aboutball.login.bean.RegisterResultBean;
import com.ifeell.app.aboutball.login.bean.RequestLoginBean;
import com.ifeell.app.aboutball.login.bean.RequestRegisterBean;
import com.ifeell.app.aboutball.login.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/8 14:26
 * 更新时间: 2019/3/8 14:26
 * 描述:
 */
public interface LoginService {
    @POST(IApiService.LOGIN)
    Observable<BaseBean<LoginResultBean>> login(@Body RequestLoginBean requestLoginBean);

    @GET(IApiService.VERIFICATION_CODE)
    Observable<BaseBean> sendMessageCode(@Query(Contast.PHONE) String phone, @Query(Contast.TYPE) int type);

   /* @GET(IApiService.USER_ACCOUNT)
    Observable<BaseBean<UserBean>> userAccount();*/

    @GET(IApiService.USER_ACCOUNT_INFO)
    Observable<BaseBean<UserBean>> userAccountInfo();

    @POST(IApiService.REGISTER)
    Observable<BaseBean<RegisterResultBean>> register(@Body RequestRegisterBean bean);

    @POST(IApiService.FORGET_PASSWORD)
    Observable<BaseBean<RegisterResultBean>> forgetPassword(@Body RequestRegisterBean bean);

    @GET(IApiService.JUDGE_MESSAGE_CODE)
    Observable<BaseBean> judgeMessageCode(@Query(Contast.PHONE) String phone,
                                          @Query(Contast.CODE) String code);

}
