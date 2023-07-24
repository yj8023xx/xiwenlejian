package com.smallc.xiwenlejian.user.service.impl;

import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.user.mapper.UserMapper;
import com.smallc.xiwenlejian.user.vo.UserVO;
import com.smallc.xiwenlejian.user.dto.UserDTO;
import com.smallc.xiwenlejian.user.dao.UserDao;
import com.smallc.xiwenlejian.user.model.User;
import com.smallc.xiwenlejian.user.service.UserService;
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
    private UserDao userDao;

    @Override
    public UserVO login(UserDTO userDTO) {
        User user = userDao.getByUsername(userDTO.getUsername());
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return null;
        }
        return UserMapper.INSTANCE.convertToVO(user);
    }

    @Override
    public UserVO getInfo(Long userId) {
        User user = userDao.getByUserId(userId);
        return UserMapper.INSTANCE.convertToVO(user);
    }

    public UserBO getByUserId(Long userId) {
        User user = userDao.getByUserId(userId);
        return UserMapper.INSTANCE.convertToBO(user);
    }

}
