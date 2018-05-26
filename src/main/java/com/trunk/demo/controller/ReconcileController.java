package com.trunk.demo.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trunk.demo.service.ReconcileFiles;

@RestController
@EnableAsync
public class ReconcileController {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}

	@Autowired
	private ReconcileFiles reconcileFiles;

	@RequestMapping(method = RequestMethod.GET, path = "/api/reconcile")
	public void reconcile() {
		reconcileFiles.reconcile();
	}

	// For testing only
	@RequestMapping(method = RequestMethod.GET, path = "/api/reset")
	public void reset() throws ParseException {
		reconcileFiles.reset();
	}

}
