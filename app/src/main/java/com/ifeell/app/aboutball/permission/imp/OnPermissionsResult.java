package com.ifeell.app.aboutball.permission.imp;

import java.util.List;
/**
 * @ClassName: OnPermissionsResult
 * @Author: 胡庆岭
 * @CreateTime: 2019/3/4 13:14
 * @UpdateTime: 2019/3/4 13:14
 * @Description:
 */

public interface OnPermissionsResult {
    void onAllow(List<String> allowPermissions);

    void onNoAllow(List<String> noAllowPermissions);

    void onForbid(List<String> noForbidPermissions);

}
