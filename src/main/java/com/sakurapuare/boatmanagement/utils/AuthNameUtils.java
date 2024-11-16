package com.sakurapuare.boatmanagement.utils;

import cn.hutool.core.lang.Validator;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;


public class AuthNameUtils {

    public static AuthName getAuthName(String name) {

        if (Validator.isEmail(name)) {
            return AuthName.EMAIL;
        } else if (Validator.isMobile(name)) {
            return AuthName.PHONE;
        } else if (Validator.isGeneralWithChinese(name) && name.length() <= 20) {
            return AuthName.USERNAME;
        }
        return AuthName.NONE;


    }

}
