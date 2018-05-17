package com.trunk.demo.service.mongo;


import com.google.gson.JsonObject;
import com.trunk.demo.Util.BCryptText;
import com.trunk.demo.model.mongo.User;
import com.trunk.demo.vo.LoginModelVO;
import com.trunk.demo.repository.TokenRepository;

import com.trunk.demo.repository.UsersRepository;
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

    @Override
    public String register(LoginModelVO loginModelVO, HttpSession session) {
        JsonObject json = new JsonObject();
        List<User> users = usersRepository.findByUsername(loginModelVO.getUsername());

        Object sessionToken = tokenRepository.getToken();
        if (sessionToken == null
                || !loginModelVO.getToken().equals(sessionToken.toString())) {
            tokenRepository.destoryToken();
            json.addProperty("result", "expired");
            return json.toString();
        }
        if (users.size() != 0) {
            json.addProperty("result", "fail");
            return json.toString();
        }
        String cipherText = bCryptText.getCipherText(loginModelVO.getPassword());
        User user = new User(loginModelVO.getUsername(),cipherText);
        usersRepository.save(user);
        json.addProperty("result", "success");

        return json.toString();
    }

    @Override
    public String loginValidator(LoginModelVO loginModelVO, HttpSession session) {
        JsonObject json = new JsonObject();
        Object sessionToken = tokenRepository.getToken();
        if (sessionToken == null
                || !loginModelVO.getToken().equals(sessionToken.toString())) {
            tokenRepository.destoryToken();
            json.addProperty("result", "expired");
            return json.toString();
        }
        List<User> users = usersRepository.findByUsername(loginModelVO.getUsername());
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
    public String userIsLogin(HttpSession session) {
        JsonObject json = new JsonObject();
        Object user = session.getAttribute(session.getId());
        if(user != null){
            session.setAttribute(session.getId(),user.toString());
            json.addProperty("result", true);
        }else{
            json.addProperty("result", false);
        }
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
