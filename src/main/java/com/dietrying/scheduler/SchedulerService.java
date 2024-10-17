package com.dietrying.scheduler;

import com.dietrying.scheduler.model.Report;
import com.dietrying.scheduler.model.User;
import com.dietrying.scheduler.model.UserTask;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class SchedulerService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Report> kafkaTemplate;

    @Scheduled(cron = "${spring.scheduled-cron}")
    void sendDailyReport() {
        List<User> users = this.userRepository.findAll();

        for (User user : users) {
            List<UserTask> tasks = this.userRepository.findUserTasks(user.getId());
            List<UserTask> uncompleted = tasks.stream().filter(task -> !task.getIsDone()).toList();
            List<UserTask> completedWithin24Hours = tasks
                    .stream()
                    .filter(task -> this.completedWithin(24, task)).toList();
            Report report = ReportFactory.form(uncompleted, completedWithin24Hours, user.getEmail());
            this.kafkaTemplate.send("EMAIL_SENDING_TASKS", report);
        }
    }

    private boolean completedWithin(int hours, UserTask task) {
        return task.getIsDone() && this.statusChangedWithin(hours, task);
    }

    private boolean statusChangedWithin(int hours, UserTask task) {
        return task
                .getStatusUpdatedAt()
                .isAfter(LocalDateTime.now().minusHours(hours));
    }
}