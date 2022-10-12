package com.lianwei.util;

import com.lianwei.domain.entity.User;
import com.lianwei.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm，用于实现用户登录授权
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 用户授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户登录验证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.login(username);
        if(user == null){
            throw new UnknownAccountException();
        }else{
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserAccount(),user.getUserPassword(), ByteSource.Util.bytes(user.getPasswordSalt()),this.getName());
            return authenticationInfo;
        }
    }
}
