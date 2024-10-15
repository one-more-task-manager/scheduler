package com.dietrying.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserTask {
    private String title;
    private Boolean isDone;
    private LocalDateTime statusUpdatedAt;
    private String todoTitle;
}
