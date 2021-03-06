package com.ifeell.app.aboutball.good;

import com.ifeell.app.aboutball.Contast;
import com.ifeell.app.aboutball.IApiService;
import com.ifeell.app.aboutball.base.BaseBean;
import com.ifeell.app.aboutball.base.BaseDataBean;
import com.ifeell.app.aboutball.good.bean.ResultGoodRefundDetailsBean;
import com.ifeell.app.aboutball.good.bean.ResultMyGoodBean;
import com.ifeell.app.aboutball.good.bean.ResultOrderDetailsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/9/12 13:58
 * 更新时间: 2019/9/12 13:58
 * 描述:
 */
public interface GoodService {
    @GET(IApiService.POST_ORDER_DETAILS)
    Observable<BaseBean<ResultOrderDetailsBean>> loadOrderDetails(@Query(Contast.SPU_ID) long souId,
                                                                  @Query(Contast.PARAMS) String params,
                                                                  @Query(Contast.NUM) int num);

    @GET(IApiService.GET_MY_GOOD_LIST)
    Observable<BaseBean<List<ResultMyGoodBean>>> loadMyGoodList(@Query(Contast.PAGE_NUM) int num,
                                                                @Query(Contast.PAGE_SIZE) int pagerSize,
                                                                @Query(Contast.STATUS) int status);

    @GET(IApiService.GET_MY_GOOD_DETAILS)
    Observable<BaseBean<ResultOrderDetailsBean>> loadGoodDetails(@Query(Contast.ORDER_ID) long orderId);

    @GET(IApiService.GET_GOOD_REFUND_DETAILS)
    Observable<BaseBean<ResultGoodRefundDetailsBean>> loadGoodRefundDetail(@Query(Contast.ORDER_ID) long orderId);

    @GET(IApiService.GET_APPLY_REFUND)
    Observable<BaseBean<BaseDataBean<String>>> applyGoodRefund(@Query(Contast.ORDER_ID) long orderId,
                                                               @Query(Contast.REASON) String reason);

    @GET(IApiService.GET_CHECK_OUT_GOOD_STATUS)
    Observable<BaseBean<String>> checkOutGoodStatus(@Query(Contast.SPU_ID) long souId,
                                                    @Query(Contast.PARAMS) String params,
                                                    @Query(Contast.NUM) int num);
}
