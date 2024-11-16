package com.sakurapuare.boatmanagement.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.mapper.CodeMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Code;
import com.sakurapuare.boatmanagement.pojo.entity.User;
import com.sakurapuare.boatmanagement.service.CodeService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-11-16
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {

    private final CodeMapper codeMapper;

    public CodeServiceImpl(CodeMapper codeMapper) {
        this.codeMapper = codeMapper;
    }

    @Override
    public boolean verifyCode(User user, String password) {
        QueryWrapper qw = QueryWrapper.create();
        qw.eq("user_id", user.getUserId()).orderBy("create_time", false);
        List<Code> codes = codeMapper.selectListWithRelationsByQuery(qw);

        if (codes.isEmpty()) {
            return false;
        }

        for (Code code : codes) {
            // parse from timestamp to LocalDateTime
            Timestamp timestamp = code.getExpirationTime();
            LocalDateTime expirationTime = timestamp.toLocalDateTime();
            if (code.getCode().equals(password)) {
                return true;
            }
            // if expired, delete the code
            if (expirationTime.isBefore(LocalDateTime.now()) || code.getIsUsed()) {
                codeMapper.deleteById(code.getId());
            }
        }

        return false;
    }

}