package com.trunk.demo.service.mongo;

import com.trunk.demo.model.viewModel.ViewLoginModel;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    public String register(ViewLoginModel viewLoginModel);

    public String loginValidator(ViewLoginModel viewLoginModel);

    public String userIsLogin();

    public String logOut();

}
