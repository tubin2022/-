package com.lianwei.service;

import com.lianwei.domain.entity.User;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.UserVO;
import org.apache.shiro.subject.Subject;

import java.util.List;


/**
 * 用户操作service接口
 */
public interface UserService {
    /**
     * 用户登录，这里是查找该登录账户的用户信息，然后交给shiro中的realm处理
     * @param username
     * @return
     */
    public User login(String username);

    /**
     * 用户注册
     * @param user
     */
    public void registe(User user);

    /**
     * 用户登出
     * @param subject
     */
    public void logout(Subject subject);

    /**
     * 修改用户的普通信息
     * @param UserVO 需要修改的用户信息
     */
    public void updateUserInfo(UserVO UserVO);

    /**
     * 修改用户密码
     * @param userId  用户id
     * @param newPassword  新密码
     * @param oldPassword  旧密码
     * @return
     */
    public ResultVO updatePassword(Integer userId,String newPassword, String oldPassword);

    /**
     * 通过用户账号查询用户信息
     * @param userAccount 用户账号
     * @return
     */
    public User findUserByUserAccount(String userAccount);

    /**
     * 通过多个用户id查询用户信息
     * @param userIds
     * @return
     */
    public List<User> findUsersByUserIds(List<Integer> userIds);
}
