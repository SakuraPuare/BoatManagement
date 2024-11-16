package com.sakurapuare.boatmanagement.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.TableName;
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
    public Code generateCode(User user) {
        Code code = Code.builder().userId(user.getUserId()).code(RandomUtil.randomNumbers(6)).expirationTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1))).build();

        codeMapper.insertSelective(code);

        return code;
    }

    @Override
    public boolean verifyCode(User user, String password) {
        QueryWrapper qw = QueryWrapper.create();
        qw.eq(TableName.USER_ID, user.getUserId()).orderBy(TableName.CREATED_AT, false);
        List<Code> codes = codeMapper.selectListByQuery(qw);

        if (codes.isEmpty()) {
            return false;
        }

        for (Code code : codes) {
            // parse from timestamp to LocalDateTime
            Timestamp timestamp = code.getExpirationTime();
            LocalDateTime expirationTime = timestamp.toLocalDateTime();
            if (code.getCode().equals(password)) {
                codeMapper.deleteById(code.getId());
                return true;
            }
            // if expired, delete the code
            if (expirationTime.isBefore(LocalDateTime.now())) {
                codeMapper.deleteById(code.getId());
            }
        }

        return false;
    }

}