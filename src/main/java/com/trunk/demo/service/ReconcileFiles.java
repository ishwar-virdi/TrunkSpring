package com.trunk.demo.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public interface ReconcileFiles {
	public String reconcile(String year,String month);
	public void reset() throws ParseException;
}
