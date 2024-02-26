package com.cardosotv.quizai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cardosotv.quizai.error.HandleException;
import com.cardosotv.quizai.security.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor{

    @Value("${com.cardoso.quizai.adminid}")
    private String tokenApp; 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response
                            , Object handler) throws Exception {
        
        String url = request.getRequestURI();
        if (url.length() > 7){
            url = url.substring(0, 7);
        }
        // if it is the authorization request not to do
        if (url.equals("/api/v1")){
            try {
                // get the token from header request           
                String token = request.getHeader("token");
                String userId = JWTUtil.getUserIdFromToken(token);    
            } catch (Throwable t) {
                throw HandleException.handleException(t, tokenApp, request.getRequestURI());
            }
        }
        // Continue processing the request
        return true;
    }

}
