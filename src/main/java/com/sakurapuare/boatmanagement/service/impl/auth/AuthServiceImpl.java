package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.UsersMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.CodesService;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth.AuthContext;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth.AuthStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.code.CodeSenderContext;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import com.sakurapuare.boatmanagement.utils.WechatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<UsersMapper, Users> implements AuthService {

    private final UsersMapper userMapper;
    private final CodesService codeService;
    private final WechatUtils wechatUtils;
    private final AuthContext authContext;

    private Users authContext(AuthStatus status, AuthRequestDTO authRequestDTO) {
        status.setName(AuthNameUtils.getAuthName(authRequestDTO.getUsername()));
        AuthContext context = new AuthContext(userMapper, codeService, wechatUtils);
        AuthStrategy strategy = context.getStrategy(status);
        strategy.configureStrategy(status);

        return strategy.auth(authRequestDTO);
    }

    @Override
    public Users loginWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Users loginWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Users registerWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Users registerWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public boolean sendCode(NameRequestDTO nameRequestDTO) {
        CodeSenderContext context = new CodeSenderContext(codeService, userMapper);
        context.setStrategy(nameRequestDTO);

        return context.sendCode(nameRequestDTO);
    }

    @Override
    public boolean checkAvailability(NameRequestDTO nameRequestDTO) {
        String[] names = {TableName.USER_USERNAME, TableName.USER_PHONE, TableName.USER_EMAIL};

        for (String field : names) {
            Users user = userMapper.selectOneByQuery(QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));
            if (user != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Users loginWithWechat(WxLoginDTO wxLoginDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.WECHAT, AuthType.LOGIN);

        // 将WxLoginDTO转换为AuthRequestDTO
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setCode(wxLoginDTO.getCode());
        authRequestDTO.setUserInfo(wxLoginDTO.getUserInfo());

        return authContext(authStatus, authRequestDTO);
    }

}
