package com.smallc.xiwenlejian.common.core.controller;


import org.springframework.http.ResponseEntity;
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
    public <T> ResponseEntity<T> onSuccess(T data) {
        return ResponseEntity.ok(data);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public ResponseEntity onFail(int status, String msg) {
        return ResponseEntity.status(status).body(msg);
    }

}
