package ru.hogwarts.school.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/port")
public class InfoController {
    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private int port;

    @GetMapping
    public int getPortInfo() {
        logger.info("Return port");
        return port;
    }
}
