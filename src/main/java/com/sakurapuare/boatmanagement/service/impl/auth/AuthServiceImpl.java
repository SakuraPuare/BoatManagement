package com.sakurapuare.boatmanagement.service.impl.auth;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.constant.auth.AuthMethod;
import com.sakurapuare.boatmanagement.constant.auth.AuthStatus;
import com.sakurapuare.boatmanagement.constant.auth.AuthType;
import com.sakurapuare.boatmanagement.mapper.AccountsMapper;
import com.sakurapuare.boatmanagement.pojo.dto.AuthRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.NameRequestDTO;
import com.sakurapuare.boatmanagement.pojo.dto.WxLoginDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Accounts;
import com.sakurapuare.boatmanagement.service.AuthService;
import com.sakurapuare.boatmanagement.service.CapthaService;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth.AuthContext;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.auth.AuthStrategy;
import com.sakurapuare.boatmanagement.service.impl.auth.strategy.captcha.CaptchaSenderContext;
import com.sakurapuare.boatmanagement.utils.AuthNameUtils;
import com.sakurapuare.boatmanagement.utils.JWTUtils;
import com.sakurapuare.boatmanagement.utils.WechatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AuthService {

    private final AccountsMapper userMapper;
    private final CapthaService capthaService;
    private final WechatUtils wechatUtils;

    private Accounts authContext(AuthStatus status, AuthRequestDTO authRequestDTO) {
        status.setName(AuthNameUtils.getAuthName(authRequestDTO.getUsername()));
        AuthContext context = new AuthContext(userMapper, capthaService, wechatUtils);
        AuthStrategy strategy = context.getStrategy(status);
        strategy.configureStrategy(status);

        return strategy.auth(authRequestDTO);
    }

    @Override
    public Accounts loginWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Accounts loginWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.LOGIN);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Accounts registerWithPassword(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.PASSWORD, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public Accounts registerWithCode(AuthRequestDTO authRequestDTO) {
        AuthStatus authStatus = new AuthStatus(AuthMethod.CODE, AuthType.REGISTER);
        return authContext(authStatus, authRequestDTO);
    }

    @Override
    public boolean sendCode(NameRequestDTO nameRequestDTO) {
        CaptchaSenderContext context = new CaptchaSenderContext(capthaService, userMapper);
        context.setStrategy(nameRequestDTO);

        return context.sendCaptcha(nameRequestDTO);
    }

    @Override
    public boolean checkAvailability(NameRequestDTO nameRequestDTO) {
        String[] names = {TableName.USER_USERNAME, TableName.USER_PHONE, TableName.USER_EMAIL};

        for (String field : names) {
            Accounts user = userMapper.selectOneByQuery(QueryWrapper.create().eq(field, nameRequestDTO.getUsername()));
            if (user != null) {
                throw new IllegalArgumentException("The username has been registered");
            }
        }

        return true;
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
