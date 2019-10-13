package com.ifeell.app.aboutball.other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/21 9:19
 * 更新时间: 2019/5/21 9:19
 * 描述:
 */
public class Pay {
    private Pay(){}
    public static class WeiChatPay{
        MessageDigest md5;

        {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
