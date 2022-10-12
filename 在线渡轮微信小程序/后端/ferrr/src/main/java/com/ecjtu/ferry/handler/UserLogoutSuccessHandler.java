package com.ecjtu.ferry.handler;

import com.ecjtu.ferry.enums.ResultStatusEnum;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 用户退出登录返回信息
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResultVO resultVO = new ResultVO(ResultStatusEnum.LOGOUT_SUCCESS);
        resultVO.out(response);
    }
}
