package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthName;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AccountsService;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth.AuthContext;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha.CaptchaSenderContext;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.sakurapuare.boatmanagement.pojo.entity.table.AccountsTableDef.ACCOUNTS;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AuthService {

    private final AccountsService accountsService;
    private final CaptchaSenderContext captchaSenderContext;

    private final AuthContext authContext;

    private Accounts authContext(AuthStatus status, AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        AuthName name = AuthNameUtils.getAuthName(username);
        status.setName(name);

        authContext.setStrategy(status);
        return authContext.auth(status, authRequestDTO);
    }

    @Override
    public Accounts loginWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Accounts authWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Accounts registerWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public boolean sendCode(NameRequestDTO nameRequestDTO) {
        captchaSenderContext.setStrategy(nameRequestDTO);

        return captchaSenderContext.sendCaptcha(nameRequestDTO);
    }

    @Override
    public boolean checkAvailability(NameRequestDTO nameRequestDTO) {
        AuthName name = AuthNameUtils.getAuthName(nameRequestDTO.getUsername());

        String field = null;
        switch (name) {
            case AuthName.USERNAME -> field = ACCOUNTS.USERNAME.getName();
            case AuthName.PHONE -> field = ACCOUNTS.PHONE.getName();
            case AuthName.EMAIL -> field = ACCOUNTS.EMAIL.getName();
            default -> field = null;
        }

        if (field == null) {
            return false;
        }

        // Accounts account = userMapper.selectOneByQuery(QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));
        Accounts account = accountsService.getOne(
                new QueryWrapper().eq(field, nameRequestDTO.getUsername()));
        return account == null;
    }

    @Override
    public Accounts loginWithWechat(WxLoginDTO wxLoginDTO) {
//         AuthStatus authStatus = new AuthStatus(AuthMethod.WECHAT, AuthType.LOGIN);

// //        // 将WxLoginDTO转换为AuthRequestDTO
//         AuthRequestDTO authRequestDTO = new AuthRequestDTO();
//         authRequestDTO.setCode(wxLoginDTO.getCode());
//         authRequestDTO.setUserInfo(wxLoginDTO.getUserInfo());

//         return authContext(authStatus, authRequestDTO);
        return null;
    }

    @Override
    public String generateToken(Accounts account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", account.getId());
        claims.put("username", account.getUsername());
        // claims.put("role", account.getRole());
        return JWTUtils.generateToken(claims);
    }

}
