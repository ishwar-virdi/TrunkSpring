package com.trunk.demo.vo;

public class RegisterModelVO extends LoginModelVO {

    private String registerCode;

    public RegisterModelVO(String username, String password, String token,String registerCode) {
        super(username, password,token);
        this.registerCode = registerCode;
    }

    public RegisterModelVO() {
    }

    public String getRegisterCode() {
        return registerCode;
    }
}
