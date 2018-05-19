package com.trunk.demo.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public interface ReconcileFiles {
	public void reconcile();
	public void reset() throws ParseException;
}
