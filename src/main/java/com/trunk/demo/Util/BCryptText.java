package com.trunk.demo.Util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptText {

    public BCryptText(){}
    public String getCipherText(String string){
        return BCrypt.hashpw(string, BCrypt.gensalt());
    }

    public Boolean isEquals(String plainText,String cipherText){
        return BCrypt.checkpw(plainText, cipherText);
    }
}
