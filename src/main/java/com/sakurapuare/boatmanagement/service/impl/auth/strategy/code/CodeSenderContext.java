package com.sakurapuare.boatmanagement.service.impl.auth.strategy.code;

import com.mybatisflex.core.query.QueryWrapper;
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

    public CodeSenderContext(CodeService codeService, UserMapper userMapper) {
        this.codeService = codeService;
        this.userMapper = userMapper;
    }

    public void setStrategy(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());

        switch (name) {
            case AuthName.EMAIL -> this.codeSender = new EmailCodeSender(codeService);
            case AuthName.PHONE -> this.codeSender = new PhoneCodeSender(codeService);
            default -> this.codeSender = null;
        }
    }

    public boolean sendCode(NameRequestDTO nameRequestDTO) {
        if (codeSender == null) {
            return false;
        }

        User user = userMapper.selectOneByQuery(
                QueryWrapper.create().eq("username", nameRequestDTO.getUsername())
        );

        return codeSender.sendCode(user);
    }

}
