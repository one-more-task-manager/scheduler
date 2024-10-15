package com.dietrying.scheduler;

import com.dietrying.scheduler.model.User;
import com.dietrying.scheduler.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.dietrying.scheduler.model.UserTask(t.title, t.isDone, t.statusUpdatedAt, todo.title) " +
            "FROM Todolist todo " +
            "JOIN Task t ON t.todolistId = todo.id " +
            "WHERE todo.userId = :userId "
    )
    List<UserTask> findUserTasks(Long userId);
}
