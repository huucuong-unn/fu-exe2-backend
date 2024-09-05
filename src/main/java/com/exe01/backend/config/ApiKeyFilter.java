//package com.exe01.backend.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class ApiKeyFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestURL = request.getRequestURL().toString();
//
//        // Bypass the API key check for Swagger URLs
//        if (requestURL.contains("/swagger-ui/") || requestURL.contains("/v3/api-docs") || requestURL.contains("/swagger-resources")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        final String apiKey = request.getHeader("X-API-KEY");
//        final String AUTH_TOKEN = "RkFNU19CQUNLRU5EX0FQSV9LRVk=";
//
//        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
//            throw new BadCredentialsException("Invalid API Key");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
