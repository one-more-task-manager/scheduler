package com.dietrying.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Report {
    private String email;
    private String title;
    private String body;
}
