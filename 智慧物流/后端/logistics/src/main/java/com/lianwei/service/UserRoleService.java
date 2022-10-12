package com.lianwei.service;

import com.lianwei.domain.entity.UserRole;

import java.util.List;

/**
 * 用户角色service
 */
public interface UserRoleService {
    /**
     * 通过用户id查询用户角色
     * @param userId
     * @return
     */
    public List<UserRole> queryUserRoleByUserId(Integer userId);
}
