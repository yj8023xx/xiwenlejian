package com.smallc.xiwenlejian.user.mapper;

import com.smallc.xiwenlejian.common.user.bo.UserBO;
import com.smallc.xiwenlejian.user.model.User;
import com.smallc.xiwenlejian.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/23
 * @since com.smallc.xiwenlejian.user.mapper
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserVO convertToVO(User user);

    UserBO convertToBO(User user);

}
