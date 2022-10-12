package com.ecjtu.ferry.handler;

import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 用户登录失败返回信息
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_LOGIN_FAIL);
        resultVO.out(response);
    }
}
