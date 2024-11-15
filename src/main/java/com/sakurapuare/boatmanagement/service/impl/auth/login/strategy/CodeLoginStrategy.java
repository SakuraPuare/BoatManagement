package com.sakurapuare.boatmanagement.service.impl.auth.login.strategy;

import com.mybatisflex.core.query.QueryWrapper;
import com.sakurapuare.boatmanagement.constant.login.Username;
import com.sakurapuare.boatmanagement.mapper.CodeMapper;
import com.sakurapuare.boatmanagement.mapper.UserMapper;
import com.sakurapuare.boatmanagement.pojo.dto.LoginRequestDTO;
import com.sakurapuare.boatmanagement.pojo.entity.Code;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CodeLoginStrategy implements LoginStrategy {

    private final UserMapper userMapper;
    private final CodeMapper codeMapper;
    private String field;

    @Autowired
    public CodeLoginStrategy(UserMapper userMapper, CodeMapper codeMapper) {
        this.userMapper = userMapper;
        this.codeMapper = codeMapper;
    }

    public void configureStrategy(Username username) {
        switch (username) {
            case USERNAME:
                this.field = "username";
                break;
            case EMAIL:
                this.field = "email";
                break;
            case PHONE:
                this.field = "phone";
                break;
            default:
                this.field = null;
        }
    }

    @Override
    public User login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();

        if (field == null) {
            return null;
        }

        User user = userMapper.selectOneByQuery(QueryWrapper.create().eq(field, username));

        if (user == null) {
            return null;
        }

        QueryWrapper qw = QueryWrapper.create();
        qw.eq("user_id", user.getUserId()).orderBy("create_time", false);
        List<Code> codes = codeMapper.selectListWithRelationsByQuery(qw);

        if (codes.isEmpty()) {
            return null;
        }

        for (Code code : codes) {
            // parse from timestamp to LocalDateTime
            Timestamp timestamp = code.getExpirationTime();
            LocalDateTime expirationTime = timestamp.toLocalDateTime();
            if (code.getCode().equals(loginRequestDTO.getPassword())) {
                return user;
            }
            // if expired, delete the code
            if (expirationTime.isBefore(LocalDateTime.now())) {
                codeMapper.deleteById(code.getId());
            }
        }

        return null;
    }
}
