package com.smylyn.TaskSubmissionApp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smylyn.TaskSubmissionApp.domain.model.Task;
import com.smylyn.TaskSubmissionApp.domain.model.User;
import com.smylyn.TaskSubmissionApp.service.TaskService;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
	@PostMapping("")
	public ResponseEntity<?> createTask(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(taskService.create(user));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getTasks(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(taskService.getTasks(user));
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTask(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(taskService.getTask(taskId));
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody Task task, @AuthenticationPrincipal User user) {
		return ResponseEntity.ok(taskService.updateTask(task));
	}

}
