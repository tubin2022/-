package com.lianwei.controller;

import com.lianwei.domain.entity.User;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.domain.vo.UserVO;
import com.lianwei.enums.ResultStatusEnum;
import com.lianwei.service.UserService;
import com.lianwei.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户操作管理接口")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param password 用户密码
     * @return
     * @throws Exception
     */
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "password",paramType = "query",dataType = "String",required = true)
    })
    @PostMapping("/login")
    public ResultVO login(String userAccount, String password) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount,password);
        subject.login(token);
        String generateToken = jwtUtil.generateToken(userAccount);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_LOGIN_SUCCESS,generateToken);
        return resultVO;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/registe")
    public ResultVO registe(@RequestBody User user){
        userService.registe(user);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    /**
     * 用户登出
     * @return
     */
    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public ResultVO logout(){
        Subject subject = SecurityUtils.getSubject();
        userService.logout(subject);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    @ApiOperation("修改用户普通信息")
    @PostMapping("/updateUserInfo")
    public ResultVO updateUserInfo(@RequestBody UserVO userVO){
        userService.updateUserInfo(userVO);
        ResultVO resultVO = new ResultVO();
        return resultVO;
    }

    /**
     * 修改用户密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return
     */
    @PostMapping("/updatePassword")
    @ApiOperation("修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "newPassword",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "oldPassword",paramType = "query",dataType = "String",required = true)
    })
    public ResultVO updatePassword(Integer userId,String newPassword,String oldPassword){
        ResultVO resultVO = userService.updatePassword(userId, newPassword, oldPassword);
        return resultVO;
    }

    @GetMapping("/userInfo")
    @ApiOperation("通过token获取用户信息")
    @ApiImplicitParam(name = "userToken",paramType = "query",dataType = "string",required = true)
    public ResultVO getUserInfoByToken(String userToken){
        String userAccount = jwtUtil.getUsername(userToken);
        User user = userService.findUserByUserAccount(userAccount);
        ResultVO resultVO = new ResultVO(user);
        return resultVO;
    }

    /**
     * 查询多个用户信息
     * @param userIds
     * @return
     */
    @GetMapping("/queryUsers")
    @ApiOperation("查询多个用户信息")
    @ApiImplicitParam(name = "userIds",paramType = "query",dataType = "String",required = true)
    public ResultVO queryUsers(@RequestParam("userIds") List<Integer> userIds){
        List<User> usersByUserIds = userService.findUsersByUserIds(userIds);
        ResultVO resultVO = new ResultVO(usersByUserIds);
        return resultVO;
    }

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public ResultVO test(){
        ResultVO resultVO = new ResultVO("test data");
        return resultVO;
    }
}
