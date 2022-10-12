package com.lianwei.dao;

import com.lianwei.domain.entity.User;
import com.lianwei.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户Dao
 */
@Mapper
public interface UserDao {
    /**
     * 通过用户账号查找用户
     * @param userAccounut
     * @return
     */
    public User findByUsername(String userAccounut);

    /**
     * 插入一条用户信息记录
     * @param user
     */
    public void insertOneUser(User user);

    /**
     * 选择字段修改用户信息
     * 根据传递的非空值进行修改,其中必需提供用户id
     * @param userVO
     */
    public void updateUserInfo(UserVO userVO);

    /**
     * 将用户信息表中是否注销字段的值进行修改
     */
    public void updateUserAccountStatus();

    /**
     * 通过用户id查询用户
     * @param userId
     * @return
     */
    public User findUserByUserId(Integer userId);


}
