package com.smallc.xiwenlejian.api.user.feign;

import com.smallc.xiwenlejian.common.user.bo.UserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.user.feign
 */
@FeignClient("xiwenlejian-user")
public interface UserFeignClient {

    @GetMapping("/feign/user/{userId}")
    UserBO getByUserId(@PathVariable("userId") Long userId);

}
