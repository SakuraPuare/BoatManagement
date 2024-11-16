package com.sakurapuare.boatmanagement.service.impl.auth.strategy;

import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.CodeService;
import org.springframework.stereotype.Component;

@Component
public class CodeStrategy extends BaseStrategy {

    private final CodeService codeService;

    public CodeStrategy(CodeService codeService, UserMapper userMapper) {
        super(userMapper);
        this.codeService = codeService;
    }

    @Override
    public User auth(AuthRequestDTO authRequestDTO) {
        User user = super.auth(authRequestDTO);

        if (user == null) {
            return null;
        }

        // check code validity
        if (codeService.verifyCode(user, authRequestDTO.getPassword())) {
            return user;
        }

        return null;
    }

}
