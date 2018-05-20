package com.trunk.demo.service.Impl;


import com.trunk.demo.Util.BCryptText;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.model.viewModel.ViewLoginModel;
import com.trunk.demo.repository.TokenRepository;

import com.trunk.demo.repository.UsersRepository;
import com.trunk.demo.service.mongo.UsersService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UsersService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptText bCryptText;
    @Autowired
    private HttpSession session;

    private final String userSession = "user";
    @Override
    public String register(ViewLoginModel viewLoginModel) {
        JSONObject json = new JSONObject();
        try {
            if (!tokenRepository.isEquals(viewLoginModel.getToken())) {
                json.put("result", "expired");
                return json.toString();
            }
            List<User> users = usersRepository.findByUsername(viewLoginModel.getUsername());
            if (users.size() != 0) {
                json.put("result", "fail");
                return json.toString();
            }
            String cipherText = bCryptText.getCipherText(viewLoginModel.getPassword());
            User user = new User(viewLoginModel.getUsername(),cipherText);
            usersRepository.save(user);
            json.put("result", "success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    @Override
    public String loginValidator(ViewLoginModel viewLoginModel) {
        JSONObject json = new JSONObject();
        try {
            if (!tokenRepository.isEquals(viewLoginModel.getToken())) {
                json.put("result", "expired");
                return json.toString();
            }
            List<User> users = usersRepository.findByUsername(viewLoginModel.getUsername());
            if (users.size() > 0
                &&bCryptText.isEquals(viewLoginModel.getPassword(),users.get(0).getPassword())){
                session.setAttribute(userSession,session.getId());
                json.put("result", "success");
                return json.toString();
            }
            json.put("result", "fail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String userIsLogin() {
        JSONObject json = new JSONObject();
        try {
            Object user = session.getAttribute(userSession);
            if(user != null){
                session.setAttribute(userSession,session.getId());
                json.put("result", true);
            }else{
                json.put("result", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String logOut() {
        JSONObject json = new JSONObject();
        try {
            session.removeAttribute(userSession);
            if(session.getAttribute(userSession) == null){
                json.put("result", true);
            }else{
                json.put("result", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


}
