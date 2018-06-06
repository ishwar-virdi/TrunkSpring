package com.trunk.demo.service.Impl;


import com.google.gson.JsonObject;
import com.trunk.demo.Util.BCryptText;
import com.trunk.demo.bo.TokenBO;
import com.trunk.demo.bo.UserBO;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.repository.TokenRepository;
import com.trunk.demo.repository.UsersRepository;
import com.trunk.demo.service.mongo.UserManager;
import com.trunk.demo.vo.LoginModelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private TokenBO tokenBO;
    @Autowired
    private UserBO userBO;
    @Autowired
    private BCryptText bCryptText;

    @Override
    public String register(LoginModelVO loginModelVO, HttpSession session) {
        JsonObject json = new JsonObject();
        List<User> users = userBO.findByUsername(loginModelVO.getUsername());

        Object sessionToken = tokenBO.getToken();
        if (sessionToken == null
                || !loginModelVO.getToken().equals(sessionToken.toString())) {
            tokenBO.destroyToken();
            json.addProperty("result", "expired");
            return json.toString();
        }
        if (users.size() != 0) {
            json.addProperty("result", "fail");
            return json.toString();
        }
        String cipherText = bCryptText.getCipherText(loginModelVO.getPassword());
        User user = new User(loginModelVO.getUsername(),cipherText);
        userBO.save(user);
        json.addProperty("result", "success");

        return json.toString();
    }

    @Override
    public String loginValidator(LoginModelVO loginModelVO, HttpSession session) {
        JsonObject json = new JsonObject();
        Object sessionToken = tokenBO.getToken();
        if (sessionToken == null
                || !loginModelVO.getToken().equals(sessionToken.toString())) {
            tokenBO.destroyToken();
            json.addProperty("result", "expired");
            return json.toString();
        }
        List<User> users = userBO.findByUsername(loginModelVO.getUsername());
        if (users.size() > 0
                &&bCryptText.isEquals(loginModelVO.getPassword(),users.get(0).getPassword())){
            session.setAttribute(session.getId(),users.get(0).getId());
            json.addProperty("result", "success");
            return json.toString();
        }
        json.addProperty("result", "fail");

        return json.toString();
    }

    @Override
    public String logOut(HttpSession session) {
        JsonObject json = new JsonObject();

        session.removeAttribute(session.getId());
        if(session.getAttribute(session.getId()) == null){
            json.addProperty("result", true);
        }else{
            json.addProperty("result", false);
        }

        return json.toString();
    }


}
