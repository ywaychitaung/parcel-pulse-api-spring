package com.parcelpulseapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {
    
    private static final Logger logger = LoggerFactory.getLogger(SwaggerRedirectController.class);

    @GetMapping("/")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        logger.info("Application is ready! Access Swagger UI at: https://parcel-pulse-api-spring-qd8aw.ondigitalocean.app/swagger-ui.html");
    }
}