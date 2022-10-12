package com.ecjtu.ferry.service;

import com.ecjtu.ferry.common.VerifyEmailFailedException;
import com.ecjtu.ferry.common.VerifyPhoneFailedException;
import com.ecjtu.ferry.pojo.User;
import com.ecjtu.ferry.pojo.vo.UserVO;

import java.util.List;


public interface IUserService {

    /**
     * 添加用户
     * @param user
     */
    public void saveUser(User user) throws VerifyPhoneFailedException, VerifyEmailFailedException;

    /**
     * 修改用户信息
     * @param user
     */
    public void updateUser(User user) throws VerifyPhoneFailedException, VerifyEmailFailedException;

    /**
     * 修改用户密码
     * @param id
     * @param newPwd
     */
    public void updatePwd(Integer id,String newPwd);

    /**
     * 更换用户手机号码
     * @param id
     * @param newPhone
     */
    public void updatePhone(Integer id,String newPhone) throws VerifyPhoneFailedException;

    /**
     * 根据条件分页查询用户信息
     * @param userVo
     * @return
     */
    public List<User> findUserByCondition(UserVO userVo);


    /**
     * 查询记录总数
     * @return
     */
    public int findAllCount(UserVO userVO);
}
