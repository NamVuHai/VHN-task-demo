package com.assignment.auth.filter;

import com.assignment.auth.services.CustomUserDetailService;
import com.assignment.auth.utils.JwtTokenUtil;
import com.assignment.core.utils.SimpleLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;
    private final HandlerExceptionResolver resolver;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailService customUserDetailService,@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailService = customUserDetailService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        List<String> whiteList = List.of("/refresh-token","/login");
        if(whiteList.contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String authHeader = request.getHeader("Authorization");
            String userName = null;
            String jwtToken = null;

            if(authHeader !=null && authHeader.startsWith("Bearer")){
                jwtToken = authHeader.substring(7);
                userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }

            if( userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userName);
                if(jwtTokenUtil.validateToken(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch (Exception e){
            SimpleLogger.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resolver.resolveException(request,response,null,e);
        }
    }
}