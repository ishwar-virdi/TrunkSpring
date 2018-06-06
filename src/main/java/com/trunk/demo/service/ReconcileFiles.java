package com.trunk.demo.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

public interface ReconcileFiles {
	public void reconcile(Set<Date> monthInvolved,String userId);
	
	//For testing
	public void reset() throws ParseException;
}
