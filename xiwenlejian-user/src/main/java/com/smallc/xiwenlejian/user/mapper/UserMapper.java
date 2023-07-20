package com.smallc.xiwenlejian.user.mapper;


import com.smallc.xiwenlejian.user.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口
 *
 * @author zhang
 * @version 1.0
 * @since com.smallc.xiwenlejian.user.mapper
 */
@Repository
public interface UserMapper {

    User getByUserId(@Param("userId") Long userId);

    User getByUsername(@Param("username") String username);

}