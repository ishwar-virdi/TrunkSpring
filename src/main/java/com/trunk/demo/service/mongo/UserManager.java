package com.trunk.demo.service.mongo;

import com.trunk.demo.vo.LoginModelVO;
import com.trunk.demo.vo.RegisterModelVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface UserManager {
    public String register(RegisterModelVO registerModelVO, HttpSession session);

    public String loginValidator(LoginModelVO loginModelVO, HttpSession session);

//    public String userIsLogin(HttpSession session);

    public String logOut(HttpSession session);

}
