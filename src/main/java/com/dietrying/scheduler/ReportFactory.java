package com.dietrying.scheduler;

import com.dietrying.scheduler.model.Report;
import com.dietrying.scheduler.model.UserTask;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ReportFactory {

    public Report form(List<UserTask> uncompleted, List<UserTask> completedWithin24Hours, String email) {
        if (!uncompleted.isEmpty() && !completedWithin24Hours.isEmpty()) {
            return new Report(
                    email,
                    "Your statistics: ",
                    body(uncompleted) + body(completedWithin24Hours)
            );
        }

        if (!uncompleted.isEmpty()) {
            return new Report(
                    email,
                    "You've got " + uncompleted.size() + " uncompleted tasks",
                    body(uncompleted)
            );
        }

        return new Report(
                email,
                "You've completed " + completedWithin24Hours.size() + " tasks within last 24 hours",
                body(completedWithin24Hours)
        );
    }

    private static String body(List<UserTask> tasks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (UserTask task : tasks) {
            String line = "Task: " + task.getTitle() + ", " + task.getTodoTitle();
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
