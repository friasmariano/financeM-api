package com.finance.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotFoundAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public NotFoundAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("application/json");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 404);
        responseBody.put("error", "Not Found");
        responseBody.put("message", "The resource was not found.");
        responseBody.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
