package com.smallc.xiwenlejian.user.feign;


import com.smallc.xiwenlejian.api.user.feign.UserFeignClient;
import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.user.feign
 */
@RestController
public class UserFeignController implements UserFeignClient {

    @Autowired
    private UserService userService;

    @Override
    public UserBO getByUserId(Long userId) {
        return userService.getByUserId(userId);
    }

}
