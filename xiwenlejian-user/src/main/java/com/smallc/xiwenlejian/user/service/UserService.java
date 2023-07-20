package com.smallc.xiwenlejian.user.service;


import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.user.vo.UserVO;
import com.smallc.xiwenlejian.user.dto.UserDTO;

/**
 * 服务类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.user.service
 */
public interface UserService {

    UserVO login(UserDTO user);

    UserVO getInfo(Long userId);

    UserBO getByUserId(Long userId);

}
