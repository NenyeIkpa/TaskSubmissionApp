package com.smylyn.TaskSubmissionApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smylyn.TaskSubmissionApp.domain.model.Task;
import com.smylyn.TaskSubmissionApp.domain.model.User;
import com.smylyn.TaskSubmissionApp.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	public Task create(User user) {
		Task task  = new Task();
		task.setStatus("to be submitted");
		task.setUser(user);
		
		return taskRepository.save(task);
	}
	
	public List<Task> getTasks(User user) {
		return taskRepository.findByUser(user);
	}
	
	public Optional<Task> getTask(Long taskId) {
		return taskRepository.findById(taskId);
	}

	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}
}
