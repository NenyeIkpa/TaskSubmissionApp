package com.smylyn.TaskSubmissionApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smylyn.TaskSubmissionApp.domain.model.Task;
import com.smylyn.TaskSubmissionApp.domain.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findByUser(User user);
}
