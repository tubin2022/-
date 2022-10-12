package com.lianwei.controller;

import com.lianwei.domain.entity.UserRole;
import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户角色controller
 */
@RestController
@RequestMapping("/role")
@Api(tags = "用户角色管理接口")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/queryRole")
    @ApiOperation("查询用户角色")
    @ApiImplicitParam(name = "userId",paramType = "query",dataType = "String",required = true)
    public ResultVO queryUserRole(Integer userId){
        List<UserRole> userRoles = userRoleService.queryUserRoleByUserId(userId);
        ResultVO resultVO = new ResultVO(userRoles);
        return resultVO;
    }
}
