package com.ifeell.app.aboutball.commonality;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.commonality.bean.RequestOtherLoginBean;
import com.ifeell.app.aboutball.commonality.bean.ResultWeiChatInfo;
import com.ifeell.app.aboutball.commonality.bean.ResultWeiChatTokenBean;
import com.ifeell.app.aboutball.login.bean.ResultThreeLoginBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/4 13:08
 * 更新时间: 2019/4/4 13:08
 * 描述:
 */
public interface WeiChatService {
    //微信登录的url
    String BASE_WEICHAT_URL = "https://api.weixin.qq.com";

    @GET(IApiService.GET_WEICHAT_TOKEN)
    Observable<ResultWeiChatTokenBean> getWeiChatToken(@Query(Contast.APPID) String appid,
                                                       @Query(Contast.SECRET) String secret,
                                                       @Query(Contast.CODE) String code,
                                                       @Query(Contast.GRANT_TYPE) String grantType);

    @GET(IApiService.GET_WEICHAT_USET_INFO)
    Observable<ResultWeiChatInfo> getWeiChatInfo(@Query(Contast.ACCESS_TOKEN) String accessToken,
                                                 @Query(Contast.OPENID) String openId);

    @POST(IApiService.OTHER_WEICHAT_LOGIN)
    Observable<BaseBean<ResultThreeLoginBean>> weiChatLogin(@Body RequestOtherLoginBean bean);

}
