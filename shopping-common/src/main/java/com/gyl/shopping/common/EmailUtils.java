package com.gyl.shopping.common;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: gyl
 * @Description: 邮箱校验&生成验证码
 */
public class EmailUtils {
    public static String generateCode(){
        List<String> strs = Arrays.asList("1","2","3","4","5","6","7","8","9","0","z","b","c","d","e","f","g","h","i","g","k","l","m");
        Collections.shuffle(strs);
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(strs.get(i));
        }
        return code.toString();
    }

    public static Boolean checkEmailAddress(String address){
        boolean result = true;
        try {
            InternetAddress internetAddress = new InternetAddress(address);
            internetAddress.validate();
        } catch (AddressException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
