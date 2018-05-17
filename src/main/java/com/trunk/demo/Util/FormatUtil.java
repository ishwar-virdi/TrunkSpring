package com.trunk.demo.Util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {

    public FormatUtil(){}

    public boolean isNum(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
