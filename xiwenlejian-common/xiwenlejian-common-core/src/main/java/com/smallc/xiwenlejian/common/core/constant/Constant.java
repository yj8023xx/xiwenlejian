package com.smallc.xiwenlejian.common.core.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 常量类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.config
 */
@Configuration
public class Constant {

    /**
     * 404状态码
     */
    public static Integer RESPONSE_CODE_NOTFOUND = 404;

    /**
     * 成功返回码
     */
    public static Integer RESPONSE_CODE_SUCCESS = 1000;

    /**
     * 请求失败返回码
     */
    public static Integer RESPONSE_CODE_FAIL = 1001;

    /**
     * 请求抛出异常返回码
     */
    public static Integer RESPONSE_CODE_EXCEPTION = 1002;

    /**
     * 未登陆状态返回码
     */
    public static Integer RESPONSE_CODE_NOLOGIN = 1003;

    /**
     * 用户不存在
     */
    public static Integer RESPONSE_CODE_UNKNOW_ACCOUNT = 1004;

    /**
     * 密码错误
     */
    public static Integer RESPONSE_CODE_ERROR_PASSWORD = 1005;

    /**
     * 404
     */
    public static String RESPONSE_MSG_NOTFOUND = "资源未找到";

    /**
     * 成功返回信息
     */
    public static String RESPONSE_MSG_SUCCESS = "操作成功";

    /**
     * 操作失败
     */
    public static String RESPONSE_MSG_FAIL = "操作失败";

    /**
     * 请求抛出异常返回信息
     */
    public static String RESPONSE_MSG_EXCEPTION = "未知异常";

    /**
     * 未登陆状态返回信息
     */
    public static String RESPONSE_MSG_NOLOGIN = "未登录";

    /**
     * 缺少参数错误
     */
    public static String RESPONSE_MSG_LACKPARAM = "缺少参数";

}
