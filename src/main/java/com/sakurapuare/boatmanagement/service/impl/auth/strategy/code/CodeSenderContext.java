package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.CodesService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public class CodeSenderContext {

    private final CodesService codeService;

    private final UsersMapper userMapper;

    private CodeSender codeSender;

    private String field = null;

    public CodeSenderContext(CodesService codeService, UsersMapper userMapper) {
        this.codeService = codeService;
        this.userMapper = userMapper;
    }

    public void setStrategy(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());

        switch (name) {
            case AuthName.EMAIL -> {
                this.codeSender = new EmailCodeSender(codeService);
                field = TableName.USER_EMAIL;
            }
            case AuthName.PHONE -> {
                this.codeSender = new PhoneCodeSender(codeService);
                field = TableName.USER_PHONE;
            }
            default -> this.codeSender = null;
        }
    }

    public boolean sendCode(NameRequestDTO nameRequestDTO) {
        if (codeSender == null) {
            return false;
        }

        Users user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));

        if (user == null) {
            return false;
        }

        return codeSender.sendCode(user);
    }

}
