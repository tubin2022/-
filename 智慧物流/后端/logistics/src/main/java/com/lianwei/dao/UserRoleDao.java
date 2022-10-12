package com.lianwei.dao;

import com.lianwei.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色dao
 */
@Mapper
public interface UserRoleDao {
    /**
     * 通过用户id查询用户角色
     * @param userId
     * @return
     */
    public List<UserRole> findUserRoleByUserId(Integer userId);
}
