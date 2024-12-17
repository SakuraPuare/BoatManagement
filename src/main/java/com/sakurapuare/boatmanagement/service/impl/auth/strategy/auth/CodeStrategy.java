package com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth;

import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.stereotype.Component;

@Component
public class CodeStrategy extends AuthStrategy {

    private final CodesService codeService;

    public CodeStrategy(CodesService codeService, UsersMapper userMapper) {
        super(userMapper);
        this.codeService = codeService;
    }

    @Override
    public Users auth(AuthRequestDTO authRequestDTO) {
        Users user = super.auth(authRequestDTO);

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
