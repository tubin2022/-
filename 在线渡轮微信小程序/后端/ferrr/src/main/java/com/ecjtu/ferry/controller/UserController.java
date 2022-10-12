package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.common.VerifyEmailFailedException;
import com.ecjtu.ferry.common.VerifyPhoneFailedException;
import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.User;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.pojo.vo.UserVO;
import com.ecjtu.ferry.service.Impl.UserServiceImpl;
import com.ecjtu.ferry.utils.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 */
@RequestMapping("/user")
@RestController
@Api(tags = "用户管理相关接口")
public class UserController {
    @Autowired
    private UserServiceImpl iUserService;

    @PostMapping("/registe")
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uname",value = "用户姓名",dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",dataType = "String",required = true),
            @ApiImplicitParam(name = "phone",value = "手机号码",dataType = "String",required = true),
            @ApiImplicitParam(name = "gender",value = " 性别",dataType = "String"),
            @ApiImplicitParam(name = "email",value = "用户邮箱",dataType = "String"),
            @ApiImplicitParam(name = "userface",value = "用户头像",dataType = "String")
    })
    public ResultVO registe(String uname, String password,String phone, String gender, String email, String userface) throws VerifyPhoneFailedException, VerifyEmailFailedException {
        User user = new User(null,uname,password,phone,gender,email,userface);
        iUserService.saveUser(user);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @GetMapping("/info")
    @ApiOperation("通过token获取用户信息")
    @ApiImplicitParam(name = "token",value = "token",dataType = "String")
    public ResultVO UserInfo(String token){
        String phone = JwtUtil.parseToken(token);
        ResultVO resultVO = new ResultVO();
        if(phone == null){
            resultVO.setCode(ResultStatusEnum.LOGIN_EXPIRATION.getCode());
            resultVO.setMsg(ResultStatusEnum.LOGIN_EXPIRATION.getMsg());
        }else{
            UserDetails userDetails = iUserService.loadUserByUsername(phone);
            User user = (User) userDetails;
            resultVO.setData(user);
        }
        return resultVO;
    }

    @ApiOperation("修改用户普通信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",required = true),
            @ApiImplicitParam(name = "uname",value = "用户姓名",dataType = "Stirng"),
            @ApiImplicitParam(name = "gender",value = "性别",dataType = "String"),
            @ApiImplicitParam(name = "email",value = "邮箱",dataType = "String"),
            @ApiImplicitParam(name = "userface",value = "用户头像",dataType = "String")
    })
    @PostMapping("/modifyInfo")
    public ResultVO modifyInfo(Integer id,String uname,String gender,String email,String userface) throws VerifyPhoneFailedException, VerifyEmailFailedException {
        User user = new User(id,uname,null,null,gender,email,userface);
        iUserService.updateUser(user);
        ResultVO resultVO = new ResultVO(ResultStatusEnum.SUCCESS);
        return resultVO;
    }

    @ApiOperation("修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",required = true),
            @ApiImplicitParam(name = "newPwd",value = "新密码",dataType = "String")
    })
    @PostMapping("/modifyPwd")
    public ResultVO modifyPwd(Integer id,String newPwd){
        iUserService.updatePwd(id,newPwd);
        ResultVO resultVO = new ResultVO((ResultStatusEnum.SUCCESS));
        return resultVO;
    }

    @ApiOperation("更换手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",required = true),
            @ApiImplicitParam(name = "newPhone",value = "新手机号码",dataType = "String")
    })
    @PostMapping("/modifyPhone")
    public ResultVO modifyPhone(Integer id,String newPhone) throws VerifyPhoneFailedException {
        iUserService.updatePhone(id,newPhone);
        ResultVO resultVO = new ResultVO((ResultStatusEnum.SUCCESS));
        return resultVO;
    }


    @ApiOperation("分页查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页",dataType = "String",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "String",required = true),
            @ApiImplicitParam(name = "uid",value = "用户id",dataType = "String"),
            @ApiImplicitParam(name = "uname",value = "用户姓名",dataType = "String"),
            @ApiImplicitParam(name = "email",value = "用户邮箱",dataType = "String"),
            @ApiImplicitParam(name = "phone",value = "手机号码",dataType = "String")
    })
    @GetMapping("/findUser")
    public ResultVO findUserByCondition(Integer pageNo,Integer pageSize,Integer uid,String uname,String email,String phone){
        Integer index = (pageNo - 1)*pageSize;
        UserVO userVO = new UserVO(index,pageSize,uid,uname,phone,email);
        List<User> users = iUserService.findUserByCondition(userVO);
        int total = iUserService.findAllCount(userVO);
        Map<String,Object> map = new HashMap<>();
        map.put("data",users);
        map.put("total",total);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        ResultVO resultVO = new ResultVO(map);
        return resultVO;
    }

}
