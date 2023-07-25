package com.smallc.xiwenlejian.user.controller;

import com.smallc.xiwenlejian.common.core.controller.BaseController;
import com.smallc.xiwenlejian.user.vo.UserVO;
import com.smallc.xiwenlejian.user.dto.UserDTO;
import com.smallc.xiwenlejian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端控制器
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.user.controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserVO> login(@RequestBody UserDTO user) {
        UserVO userVO = userService.login(user);
        if (userVO == null) {
            return onFail(HttpStatus.UNAUTHORIZED.value(),"用户名或密码错误");
        }
        return onSuccess(userVO);
    }

    /**
     *
     * @param userId 用户的id
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserVO> getInfo(@PathVariable Long userId) {
        UserVO userVO = userService.getInfo(userId);
        if (userVO == null) {
            return onFail(HttpStatus.NOT_FOUND.value(), "用户不存在");
        }
        return onSuccess(userVO);
    }

}
