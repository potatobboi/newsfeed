package com.sparta.newsfeed.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.security.UserDetailsImpl;
import com.sparta.newsfeed.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가 필터")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = jwtUtil.getTokenFromRequest(request);

        if (!(tokenValue == null)) {

            if (jwtUtil.isValidToken(tokenValue)) {
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
                String username = info.getSubject();

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetailsImpl userDetails = userService.getUserDetailsByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            } else {
                response.setStatus(400);
                response.setContentType("application/jason; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(new CommonResponseDto("유효하지 않은 토큰입니다.", 400)));
            }

        }

        filterChain.doFilter(request, response);
    }
}
