package com.trunk.demo.service.mongo;

import com.trunk.demo.model.LoginDetails;

public interface UserManager {
	public String register(String username, String password);

	public String loginValidator(LoginDetails details);
}
