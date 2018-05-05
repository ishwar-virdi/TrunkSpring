package com.trunk.demo.service.mongo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.trunk.demo.interfaces.mongo.UsersRepository;
import com.trunk.demo.model.LoginDetails;
import com.trunk.demo.model.mongo.User;

@EnableMongoRepositories(basePackages = "com.trunk.demo.interfaces")
@Service
public class UserManagerImpl implements UserManager{

	@Autowired
	private UsersRepository usersRepo;

	public String loginValidator(LoginDetails details) {

		JSONObject response = new JSONObject();
		boolean isResultBad = false;
		List<User> searchList = new ArrayList<>(); 

		searchList.addAll(usersRepo.findByUsername(details.getUsername()));

		if (searchList.size() != 0) {
			if (!searchList.get(0).getPassword().equals(details.getPassword()))
				isResultBad = true;
		} else
			isResultBad = true;

		try {
			if (isResultBad)
				response.put("result", "fail");
			else
				response.put("result", "success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

	public String register(String username, String password) {

		JSONObject response = new JSONObject();
		boolean isResultBad = false;
		List<User> searchList = new ArrayList<>(); 
		
		
		searchList.addAll(usersRepo.findByUsername(username));

		if (searchList.size() != 0)
			isResultBad = true;
		else {
			User newUser = new User(username, password);
			usersRepo.insert(newUser);
		}
		try {
			if (isResultBad)
				response.put("result", "fail");
			else
				response.put("result", "success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

}
