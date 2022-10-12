package com.ecjtu.ferry.dao;

import com.ecjtu.ferry.pojo.User;
import com.ecjtu.ferry.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 添加用户
     * @param user      用户信息实体
     */
    public void addUser(User user);

    /**
     * 修改用户信息(修改普通信息)
     * @param user  用户信息实体
     */
    public void updateUser(User user);

    /**
     * 查询用户(根据条件查询)
     * 这里用于一些可能重复的用户信息进行模糊查询
     * @param userVO    封装的条件实体
     * @return
     */
    public List<User> findUserByCondition(UserVO userVO);

    /**
     * 根据用户手机号码查询
     * 本系统登录是根据用户的手机号码查询，所以用户手机号码唯一
     * @param phone     用户手机号码
     * @return
     */
    public User findByPhone(String phone);

    /**
     * 查询总数
     * @param userVO
     * @return
     */
    public int findAllCount(UserVO userVO);
}
