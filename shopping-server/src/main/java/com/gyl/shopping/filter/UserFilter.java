package com.gyl.shopping.filter;

import com.gyl.shopping.common.ExceptionEnum;
import com.gyl.shopping.common.MallException;
import com.gyl.shopping.common.TokenUtil;
import com.gyl.shopping.dao.UserMapper;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.service.UserService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    public static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    @Resource
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("token");
        Integer userId = TokenUtil.verifyToken(token);
        if (userId <= 0) {
            throw new MallException(ExceptionEnum.LOGOUT);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new MallException(ExceptionEnum.NOT_USER);
        }
        currentUser.set(user);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
