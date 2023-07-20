package com.smallc.xiwenlejian.common.core.controller;


import com.smallc.xiwenlejian.common.core.constant.Constant;
import com.smallc.xiwenlejian.common.core.vo.ResultVO;
import org.springframework.stereotype.Component;

/**
 * 前端控制器
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.controller
 */
@Component
public class BaseController {

    /**
     * 请求成功
     *
     * @return
     */
    public ResultVO onSuccess(Object data) {
        return new ResultVO(Constant.RESPONSE_CODE_SUCCESS, Constant.RESPONSE_MSG_SUCCESS, data);
    }

    public ResultVO onSuccess() {
        return new ResultVO(Constant.RESPONSE_CODE_SUCCESS, Constant.RESPONSE_MSG_SUCCESS);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public ResultVO onFail(Integer code, String msg) {
        return new ResultVO(code, msg);
    }

    public ResultVO onFail(Integer code) {
        return new ResultVO(code, Constant.RESPONSE_MSG_FAIL);
    }

    public ResultVO onFail(String msg) {
        return new ResultVO(Constant.RESPONSE_CODE_FAIL, msg);
    }

    public ResultVO onFail() {
        return new ResultVO(Constant.RESPONSE_CODE_FAIL, Constant.RESPONSE_MSG_FAIL);
    }

}
