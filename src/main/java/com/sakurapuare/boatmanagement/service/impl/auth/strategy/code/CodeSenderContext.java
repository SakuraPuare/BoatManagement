package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.CodeService;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import org.springframework.stereotype.Component;

@Component
public class CodeSenderContext {

    private final CodeService codeService;

    private final UserMapper userMapper;

    private CodeSender codeSender;

    private String field = null;

    public CodeSenderContext(CodeService codeService, UserMapper userMapper) {
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

        User user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));

        if (user == null) {
            return false;
        }

        return codeSender.sendCode(user);
    }

}
