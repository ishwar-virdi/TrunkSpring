package com.trunk.demo.service;

import java.text.ParseException;

public interface ReconcileFiles {
	public void reconcile();
	
	//For testing
	public void reset() throws ParseException;
}
