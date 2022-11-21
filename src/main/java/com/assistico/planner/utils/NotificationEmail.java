package com.assistico.planner.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;
}
