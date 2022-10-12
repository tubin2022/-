package com.ecjtu.ferry.handler;

import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 用户未登录返回信息
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_NOT_LOGIN);
        resultVO.out(response);
    }
}
