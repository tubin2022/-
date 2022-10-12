package com.ecjtu.ferry.service.Impl;

import com.ecjtu.ferry.common.VerifyEmailFailedException;
import com.ecjtu.ferry.common.VerifyPhoneFailedException;
import com.ecjtu.ferry.dao.UserDao;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.User;
import com.ecjtu.ferry.pojo.vo.UserVO;
import com.ecjtu.ferry.service.IUserService;
import com.ecjtu.ferry.utils.VerifyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理Service
 */

@Service
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserDao userDao;

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void saveUser(User user) throws VerifyPhoneFailedException, VerifyEmailFailedException {
        //验证手机号码格式
        String phone = user.getPhone();
        boolean verify = VerifyData.verify(phone);
        if(!verify){
            throw new VerifyPhoneFailedException("手机号码格式错误");
        }
        System.out.println(user.getEmail()=="");
        if(user.getEmail() != null &&!("".equals(user.getEmail()))){
            //用户提供了邮箱，验证邮箱
            boolean b = VerifyData.verifyEmail(user.getEmail());
            if(!b){
                // 验证失败
                throw new VerifyEmailFailedException("邮箱格式错误");
            }
        }
        String password = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodePassword);
        userDao.addUser(user);
    }

    /**
     * 修改用户信息
     * @param user
     */
    @Override
    public void updateUser(User user) throws VerifyPhoneFailedException, VerifyEmailFailedException {
        if(user.getEmail() != null && !(" ".equals(user.getEmail()))){
            //用户提供了邮箱，验证邮箱
            boolean b = VerifyData.verifyEmail(user.getEmail());
            if(b){
                //验证成功
                userDao.updateUser(user);
            }else{
                // 验证失败
                throw new VerifyEmailFailedException("邮箱格式错误");
            }
        }
        userDao.updateUser(user);
    }

    /**
     * 修改用户密码
     * @param id
     * @param newPwd
     */
    @Override
    public void updatePwd(Integer id,String newPwd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePwd = encoder.encode(newPwd);
        User user = new User(id,null,encodePwd,null,null,null,null);
        userDao.updateUser(user);
    }

    /**
     * 更换用户手机号码
     * 这里单独拿出来是因为系统登录用的是手机号码登录
     * @param id
     * @param newPhone
     */
    @Override
    public void updatePhone(Integer id, String newPhone) throws VerifyPhoneFailedException {
        //验证手机号码格式
        boolean verify = VerifyData.verify(newPhone);
        if(!verify){
            throw new VerifyPhoneFailedException("手机号码格式错误");
        }
        User user = new User(id,null,null,newPhone,null,null,null);
        userDao.updateUser(user);
    }

    /**
     * 根据条件分页查询用户信息
     * @param userVo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findUserByCondition(UserVO userVo) {
        return userDao.findUserByCondition(userVo);
    }

    /**
     * 查询记录总数
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public int findAllCount(UserVO userVO) {
        return userDao.findAllCount(userVO);
    }


    /**
     * springSecurity安全框架里需要实现的方法
     * 根据用户名（本系统是手机号码）查询用户信息
     * @param phone
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User userDetails = null;
        userDetails = userDao.findByPhone(phone);
        if(userDetails == null){
            throw new UsernameNotFoundException(ResultStatusEnum.USER_NOT_EXIST.getMsg());
        }else{
            return (UserDetails) userDetails;
        }

    }
}
