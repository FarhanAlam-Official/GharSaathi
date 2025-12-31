package com.gharsaathi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class GharSaathiApplication {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(GharSaathiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String serverPort = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        
        log.info("\n" +
            "═══════════════════════════════════════════════════════════════════════\n" +
            "    ____  _                 ____                  _   _     _          \n" +
            "   / ___|| |__   __ _ _ __ / ___|  __ _  __ _  _| |_| |__ (_)         \n" +
            "  | |  _ | '_ \\ / _` | '__| |  _ / _` |/ _` |/ _` | __| '_ \\| |      \n" +
            "  | |_| || | | | (_| | |  | |_| | (_| | (_| | (_| | |_| | | | |       \n" +
            "   \\____||_| |_|\\__,_|_|   \\____|\\__,_|\\__,_|\\__,_|\\__|_| |_|_|   \n" +
            "                                                                         \n" +
            "   Real Estate & Property Management Platform                        \n" +
            "═══════════════════════════════════════════════════════════════════════\n" +
            "   Application:  GharSaathi Backend API                              \n" +
            "   Version:      1.0.0                                               \n" +
            "   Port:         " + serverPort + "                                  \n" +
            "   Profile:      " + String.join(", ", environment.getActiveProfiles().length > 0 
                ? environment.getActiveProfiles() : new String[]{"default"}) + "   \n" +
            "   API Docs:     http://localhost:" + serverPort + contextPath + "/swagger-ui.html\n" +
            "   Health:       http://localhost:" + serverPort + contextPath + "/api/auth/health\n" +
            "═══════════════════════════════════════════════════════════════════════\n" +
            "   Available Endpoints:                                              \n" +
            "      POST /api/auth/register  (Register new user)                   \n" +
            "      POST /api/auth/login     (User authentication)                 \n" +
            "      GET  /api/auth/health    (Health check)                        \n" +
            "═══════════════════════════════════════════════════════════════════════\n" +
            "   Application is ready and running!                                \n" +
            "═══════════════════════════════════════════════════════════════════════\n"
        );
    }

}
