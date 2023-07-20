package com.smallc.xiwenlejian.user.service.impl;

import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.user.vo.UserVO;
import com.smallc.xiwenlejian.user.dto.UserDTO;
import com.smallc.xiwenlejian.user.mapper.UserMapper;
import com.smallc.xiwenlejian.user.model.User;
import com.smallc.xiwenlejian.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.user.service.impl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO login(UserDTO user) {
        User userDO = userMapper.getByUsername(user.getUsername());
        if (!userDO.getPassword().equals(user.getPassword())) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    @Override
    public UserVO getInfo(Long userId) {
        User userDO = userMapper.getByUserId(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    public UserBO getByUserId(Long userId) {
        User userDO = userMapper.getByUserId(userId);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(userDO, userBO);
        return userBO;
    }

}
