package com.ecjtu.ferry.handler;


import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.User;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    /**
     * 登录成功后，返回token给用户，token过期时间为5小时
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //从身份证中获取用户信息
        User user = (User) authentication.getPrincipal();
        //把用户信息存入 安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String phone = user.getPhone();
        String token = JwtUtil.getToken(phone);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        ResultVO resultVO = new ResultVO(map);
        resultVO.out(response);
    }
}
