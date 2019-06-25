package com.work.guaishouxingqiu.aboutball.game;

import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.IApiService;
import com.work.guaishouxingqiu.aboutball.base.BaseBean;
import com.work.guaishouxingqiu.aboutball.base.BaseDataBean;
import com.work.guaishouxingqiu.aboutball.game.bean.RequestGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCollectionBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameCommentBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataInfoBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDataResultBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameDetailsBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameFiltrateBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameGroupBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameInfoScoreboardBean;
import com.work.guaishouxingqiu.aboutball.game.bean.ResultGameSimpleBean;
import com.work.guaishouxingqiu.aboutball.home.bean.RequestSendMessageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/15 14:25
 * 更新时间: 2019/3/15 14:25
 * 描述: 比赛相关的接口
 */
public interface GameService {
    @GET(IApiService.GAME_LIST)
    Observable<BaseBean<List<ResultGameBean>>> loadGameData(@Query(Contast.OFFICIAL) int gameType);

    @GET(IApiService.GAME_REFRESH_LIST)
    Observable<BaseBean<List<ResultGameBean>>> loadGameRefreshData(@Query(Contast.OFFICIAL) int gameType,
                                                                   @Query(Contast.DATE) String date);

    @GET(IApiService.GAME_MORE_LIST)
    Observable<BaseBean<List<ResultGameBean>>> loadGameMoreData(@Query(Contast.OFFICIAL) int gameType,
                                                                @Query(Contast.DATE) String date);

    @GET(IApiService.GET_MATCH_SIMPLE)
    Observable<BaseBean<ResultGameSimpleBean>> loadGameSimple(@Query(Contast.MATCH_ID) int gameId);

    @GET(IApiService.GET_MATCH_RESULT_DATA)
    Observable<BaseBean<List<ResultGameDataBean>>> loadGameResultData(@Query(Contast.MATCH_ID) int gameId,
                                                                      @Query(Contast.PAGE_NUM) int pageNum,
                                                                      @Query(Contast.PAGE_SIZE) int pageSize);

    @GET(IApiService.GET_MATCH_COMMENT_LIST)
    Observable<BaseBean<List<ResultGameCommentBean>>> loadCommentList(@Query(Contast.MATCH_ID) int gameId,
                                                                      @Query(Contast.PAGE_NUM) int pageNum,
                                                                      @Query(Contast.PAGE_SIZE) int pageSize);

    @POST(IApiService.POST_SEND_COMMENT_CONTENT)
    Observable<BaseBean<BaseDataBean>> sendCommentMessage(@Body RequestGameCommentBean bean);

    @GET(IApiService.GET_MATCH_DETAILS)
    Observable<BaseBean<ResultGameDataInfoBean>> loadGameHeadDetails(@Query(Contast.MATCH_ID) int gameId);

    @GET(IApiService.GET_COLLECTION_VIDEO_LIST)
    Observable<BaseBean<List<ResultGameCollectionBean>>> loadCollectionList(@Query(Contast.MATCH_ID) int gameId);

    @GET(IApiService.GET_MATCH_RESULT_DETAILS)
    Observable<BaseBean<List<ResultGameDataResultBean>>> loadGameDataResult(@Query(Contast.MATCH_ID) long gameId);

    @GET(IApiService.GET_MATCH_INTO_FILTRATE)
    Observable<BaseBean<List<ResultGameFiltrateBean>>> loadGameInfoFiltrateData();

    @GET(IApiService.GET_MATCH_INFO_GROUP)
    Observable<BaseBean<List<ResultGameGroupBean>>> loadMatchGroupData(@Query(Contast.GAME_ID) long gameId);
    @GET(IApiService.GET_MATCH_INFO_SCOREBOARD)
    Observable<BaseBean<List<ResultGameInfoScoreboardBean>>> loadMatchScoreboardData(@Query(Contast.GAME_ID) long gameId,
                                                                                     @Query(Contast.GROUP_ID) long groupId);

}
