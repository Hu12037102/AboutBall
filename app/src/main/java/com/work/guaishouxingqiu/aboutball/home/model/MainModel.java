package com.work.guaishouxingqiu.aboutball.home.model;

import android.support.annotation.NonNull;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.BaseModel;
import com.work.guaishouxingqiu.aboutball.base.imp.IBaseModelCallback;
import com.work.guaishouxingqiu.aboutball.home.bean.MainTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/6 10:39
 * 更新时间: 2019/3/6 10:39
 * 描述: 主页面的model
 */
public class MainModel extends BaseModel {

    public interface OnCallback extends IBaseModelCallback {
        void onResultTabData(List<MainTabBean> data);
    }



    public void loadMainTab(@NonNull String[] tabNameArray,OnCallback onCallback) {

        List<MainTabBean> data = new ArrayList<>();
        for (int i = 0; i < tabNameArray.length; i++) {
            switch (i) {
                case 0:
                    MainTabBean homeTabBean = new MainTabBean(tabNameArray[0]);
                    homeTabBean.isChecked = true;
                    homeTabBean.mCheckResIcon = R.mipmap.icon_main_tab_home_check;
                    homeTabBean.mDefaultResIcon = R.mipmap.icon_main_tab_home_default;
                    data.add(homeTabBean);
                    break;
                case 1:
                    MainTabBean gameTabBean = new MainTabBean(tabNameArray[1]);
                    gameTabBean.mCheckResIcon = R.mipmap.icon_main_tab_game_check;
                    gameTabBean.mDefaultResIcon = R.mipmap.icon_main_tab_game_default;
                    data.add(gameTabBean);
                    break;
                case 2:
                    MainTabBean venueTabBean = new MainTabBean(tabNameArray[2]);
                    venueTabBean.mCheckResIcon = R.mipmap.icon_main_tab_venues_check;
                    venueTabBean.mDefaultResIcon = R.mipmap.icon_main_tab_venues_default;
                    data.add(venueTabBean);
                    break;
               /* case 3:
                    MainTabBean communityTabBean = new MainTabBean(tabNameArray[3]);
                    communityTabBean.mCheckResIcon = R.mipmap.icon_main_tab_community_check;
                    communityTabBean.mDefaultResIcon = R.mipmap.icon_main_tab_community_default;
                    data.add(communityTabBean);
                    break;*/
                case 3:
                    MainTabBean myTabBean = new MainTabBean(tabNameArray[3]);
                    myTabBean.mCheckResIcon = R.mipmap.icon_main_tab_my_check;
                    myTabBean.mDefaultResIcon = R.mipmap.icon_main_tab_my_default;
                    data.add(myTabBean);
                    break;
                default:
                    break;
            }

        }
        onCallback.onResultTabData(data);
    }


}
