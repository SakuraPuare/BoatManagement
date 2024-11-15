package com.sakurapuare.boatmanagement.utils;

import cn.hutool.core.lang.Validator;
import com.sakurapuare.boatmanagement.constant.login.LoginStatus;
import com.sakurapuare.boatmanagement.constant.login.Username;

public class LoginTypeUtils {

    public static LoginStatus getLoginType(String username) {
        LoginStatus loginStatus = new LoginStatus();

        if (Validator.isEmail(username)) {
            loginStatus.setUsername(Username.EMAIL);
        } else if (Validator.isMobile(username)) {
            loginStatus.setUsername(Username.PHONE);
        } else if (Validator.isGeneral(username, 5, 20)) {
            loginStatus.setUsername(Username.USERNAME);
        }

        return loginStatus;
    }


}
