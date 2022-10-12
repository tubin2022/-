package com.lianwei.service.impl;

import com.lianwei.dao.UserDao;
import com.lianwei.domain.entity.User;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.UserVO;
import com.lianwei.enums.ResultStatusEnum;
import com.lianwei.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户service的实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录，根据登录账户查出用户信息，然后交给shiro的Realm处理
     * @param username
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public User login(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void registe(User user) {
        String password = user.getUserPassword();
        user.setPasswordSalt(UUID.randomUUID().toString()+user.getUserAccount());
        Md5Hash md5Hash = new Md5Hash(password,user.getPasswordSalt(),64);
        user.setUserPassword(md5Hash.toString());
        user.setRegisteTime(new Date());
        String nowFace = user.getNowFace();
        List<String> passFace = user.getPassFace();
        if(passFace == null){
            passFace = new ArrayList<>();
            passFace.add(nowFace);
        }else{
            passFace.add(nowFace);
        }
        user.setPassFace(passFace);
        userDao.insertOneUser(user);
    }

    /**
     * 用户登出
     * @param subject
     */
    @Override
    public void logout(Subject subject) {
        subject.logout();
    }

    /**
     * 修改用户普通信息
     * @param userVO 需要修改的用户信息
     */
    @Override
    public void updateUserInfo(@RequestBody UserVO userVO) {
        String nowFace = userVO.getNowFace();
        if(nowFace != null){
            //代表用户更换了头像，将新头像存入到历史头像中
            List<String> passFace = userVO.getPassFace();
            if(passFace == null){
                passFace =  new ArrayList<>();
                passFace.add(nowFace);
            }else{
                passFace.add(nowFace);
            }
            userVO.setPassFace(passFace);
        }
        userDao.updateUserInfo(userVO);
    }

    /**
     * 修改用户密码
     * @param userId  用户id
     * @param newPassword  新密码
     * @param oldPassword  旧密码
     * @return
     */
    @Override
    public ResultVO updatePassword(Integer userId,String newPassword, String oldPassword) {
        //通过用户id查询用户，比较旧密码，比对成功进行修改，否则返回错误信息
        User userByUserId = userDao.findUserByUserId(userId);
        Md5Hash md5Hash = new Md5Hash(oldPassword,userByUserId.getPasswordSalt(),64);
        if(md5Hash.toString().equals(userByUserId.getUserPassword())){
            UserVO userVO = new UserVO();
            userVO.setUserId(userId);
            Md5Hash md5Hash1 = new Md5Hash(newPassword,userByUserId.getPasswordSalt(),64);
            userVO.setUserPassword(md5Hash1.toString());
            userDao.updateUserInfo(userVO);
            return new ResultVO();
        }else{
            ResultVO resultVO = new ResultVO(ResultStatusEnum.ERROR_OLDPASSWORD);
            return resultVO;
        }
    }

    /**
     * 通过用户登录账号查询用户信息
     * @param userAccount 用户账号
     * @return
     */
    @Override
    public User findUserByUserAccount(String userAccount) {
        return userDao.findByUsername(userAccount);
    }

    /**
     * 通过多个用户ids查询用户信息
     * @param userIds
     * @return
     */
    @Override
    public List<User> findUsersByUserIds(List<Integer> userIds) {
        List<User> users = new ArrayList<>();
        for (Integer userId :userIds){
            User userByUserId = userDao.findUserByUserId(userId);
            users.add(userByUserId);
        }
        return users;
    }
}
