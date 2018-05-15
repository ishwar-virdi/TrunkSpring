package com.trunk.demo.service.mongo;


import com.google.gson.JsonObject;
import com.trunk.demo.Util.BCryptText;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.model.viewModel.ViewLoginModel;
import com.trunk.demo.repository.TokenRepository;

import com.trunk.demo.repository.UsersRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("userServiceImpl")
public class UserManagerImpl implements UserManager {

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
        JsonObject json = new JsonObject();
        if (!tokenRepository.isEquals(viewLoginModel.getToken())) {
            json.addProperty("result", "expired");
            return json.toString();
        }
        List<User> users = usersRepository.findByUsername(viewLoginModel.getUsername());
        if (users.size() != 0) {
            json.addProperty("result", "fail");
            return json.toString();
        }
        String cipherText = bCryptText.getCipherText(viewLoginModel.getPassword());
        User user = new User(viewLoginModel.getUsername(),cipherText);
        usersRepository.save(user);
        json.addProperty("result", "success");

        return json.toString();
    }

    @Override
    public String loginValidator(ViewLoginModel viewLoginModel) {
        JsonObject json = new JsonObject();

        if (!tokenRepository.isEquals(viewLoginModel.getToken())) {
            json.addProperty("result", "expired");
            return json.toString();
        }
        List<User> users = usersRepository.findByUsername(viewLoginModel.getUsername());
        if (users.size() > 0
                &&bCryptText.isEquals(viewLoginModel.getPassword(),users.get(0).getPassword())){
            session.setAttribute(userSession,session.getId());
            json.addProperty("result", "success");
            return json.toString();
        }
        json.addProperty("result", "fail");

        return json.toString();
    }

    @Override
    public String userIsLogin() {
        JsonObject json = new JsonObject();

        Object user = session.getAttribute(userSession);
        if(user != null){
            session.setAttribute(userSession,session.getId());
            json.addProperty("result", true);
        }else{
            json.addProperty("result", false);
        }

        return json.toString();
    }

    @Override
    public String logOut() {
        JsonObject json = new JsonObject();

        session.removeAttribute(userSession);
        if(session.getAttribute(userSession) == null){
            json.addProperty("result", true);
        }else{
            json.addProperty("result", false);
        }

        return json.toString();
    }


}
