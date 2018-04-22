package com.trunk.demo.pojo;

import java.util.List;

public class MatchFiles {
	
	private List<String> fileKeyNames;

	public MatchFiles(List<String> fileKeyNames) {
		super();
		this.fileKeyNames = fileKeyNames;
	}

	public MatchFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<String> getFileKeyNames() {
		return fileKeyNames;
	}

	public void setFileKeyNames(List<String> fileKeyNames) {
		this.fileKeyNames = fileKeyNames;
	}
	
	
	
}
