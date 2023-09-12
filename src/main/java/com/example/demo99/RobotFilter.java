package com.example.demo99;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;

public class RobotFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
   //System.out.println("hellow from robot");
       //0. Should execute filter?
        if(!Collections.list(request.getHeaderNames()).contains(HEADER_NAME)){
            filterChain.doFilter(request,response);
            return;
        }

        //1. Authentication decision
        var password=request.getHeader(HEADER_NAME);
        if (!"beep-boop".equals(password)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Context-type","text/plain;charset=utf-8");
           response.getWriter().println("you are not ms robot");
      return;
        } else {
            var newContext= SecurityContextHolder.createEmptyContext();
           newContext.setAuthentication(new RobotAuthentication());
           SecurityContextHolder.setContext(newContext);
           filterChain.doFilter(request,response);
           return;
        }
        //2. Do the Rest

    }
}
