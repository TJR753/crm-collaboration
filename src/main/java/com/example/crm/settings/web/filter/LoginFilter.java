package com.example.crm.settings.web.filter;

import com.example.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        if("/login.jsp".equals(request.getServletPath())||"/settings/user/login.do".equals(request.getServletPath())){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            User user = (User)request.getSession().getAttribute("user");
            if(user==null){
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
    }
}
