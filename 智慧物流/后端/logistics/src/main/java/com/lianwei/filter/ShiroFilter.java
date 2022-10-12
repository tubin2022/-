package com.lianwei.filter;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.enums.ResultStatusEnum;
import com.lianwei.util.JwtUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * shiro的过滤器
 */
public class ShiroFilter extends FormAuthenticationFilter {
    //实例化jwt工具类，这里因为spring中Bean的生命周期缘故，注入没有用，需要手动实例化
    private JwtUtil jwtUtil = new JwtUtil();

    /**
     * 判断用户是否登录，已经登录是否有效，即token是否过期
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        boolean accessAllowed = super.isAccessAllowed(request, response, mappedValue);
//        if(!accessAllowed){//没有通过登录验证
//            return false;
//        }else {//通过了登录验证，判断token是否过期
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader("token");
            if (token == null) {
                return false;
            } else {
                return !jwtUtil.isExpires(token);
            }
//        }
    }

    /**
     * 如果确定用户未登录，即isAccessAllowed方法返回false，进入该方法，返回用户未登录错误信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ResultVO resultVO = new ResultVO(ResultStatusEnum.USER_NOT_LOGIN);
        resultVO.out(httpServletResponse);
        return false;
    }


}
