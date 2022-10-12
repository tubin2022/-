package com.lianwei.service.impl;

import com.lianwei.dao.UserRoleDao;
import com.lianwei.domain.entity.UserRole;
import com.lianwei.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色service实现类
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 通过用户id查询用户角色
     * @param userId
     * @return
     */
    @Override
    public List<UserRole> queryUserRoleByUserId(Integer userId) {
        return userRoleDao.findUserRoleByUserId(userId);
    }
}
