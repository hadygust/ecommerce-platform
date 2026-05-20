package com.hadygust.notification.service;

import com.hadygust.notification.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendEmail(Notification notification){
        log.info("Email sent for msg: {}", notification.toString());
    }

}
